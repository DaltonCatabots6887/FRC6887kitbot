package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPLTVController;

public class DriveSubsystem extends SubsystemBase {
  
  // 1. Declare motors (kBrushed is correct for CIMs)
  private final SparkMax leftLeader = new SparkMax(11, MotorType.kBrushless);
  private final SparkMax leftFollower = new SparkMax(13, MotorType.kBrushless);
  
  private final SparkMax rightLeader = new SparkMax(12, MotorType.kBrushless);
  private final SparkMax rightFollower = new SparkMax(14, MotorType.kBrushless);

  private final RelativeEncoder leftEncoder;
  private final RelativeEncoder rightEncoder;
  
  private final ADIS16470_IMU m_gyro = new ADIS16470_IMU();
  private final DifferentialDrive m_drive = new DifferentialDrive(leftLeader, rightLeader);

  private final DifferentialDriveOdometry m_odometry;

  private final double trackWidthMeters = 0.213; 
  private final DifferentialDriveKinematics m_kinematics = new DifferentialDriveKinematics(trackWidthMeters);

  public DriveSubsystem() {
    SparkMaxConfig leftConfig = new SparkMaxConfig();
    SparkMaxConfig rightConfig = new SparkMaxConfig();

    double conversionFactor = 0.056;
    
    // --- RIGHT SIDE SETUP ---
    rightConfig.encoder
        .positionConversionFactor(conversionFactor)
        .velocityConversionFactor(conversionFactor / 60.0);
    rightLeader.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightConfig.follow(rightLeader); 
    rightFollower.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // --- LEFT SIDE SETUP ---
    leftConfig.inverted(true); // Invert left side so forward is forward
    leftConfig.encoder
        .positionConversionFactor(conversionFactor)
        .velocityConversionFactor(conversionFactor / 60.0);
    leftLeader.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    leftConfig.follow(leftLeader);
    leftFollower.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // --- GRAB ENCODER OBJECTS ---
    leftEncoder = leftLeader.getEncoder();
    rightEncoder = rightLeader.getEncoder();

    // --- ODOMETRY SETUP ---
    resetGyro();
    m_odometry = new DifferentialDriveOdometry(
        Rotation2d.fromDegrees(-m_gyro.getAngle()), 
        leftEncoder.getPosition(), 
        rightEncoder.getPosition()
    );

    // --- PATHPLANNER AUTOBUILDER CONFIG ---
    RobotConfig config;
    try {
      config = RobotConfig.fromGUISettings();
    } catch (Exception e) {
      e.printStackTrace();
      config = null; 
    }

    AutoBuilder.configure(
      this::getPose, 
      this::resetPose, 
      this::getChassisSpeeds, 
      (speeds, feedforwards) -> driveRobotRelative(speeds), 
      new PPLTVController(0.02), // PathPlanner's recommended controller for Tank Drives
      config, 
      () -> {
        // Automatically flip paths if you are on the Red Alliance
        var alliance = DriverStation.getAlliance();
        if (alliance.isPresent()) {
          return alliance.get() == DriverStation.Alliance.Red;
        }
        return false;
      },
      this // Reference to this subsystem
    );
  }

@Override
  public void periodic() {
    // 3. Update Odometry constantly so PathPlanner knows exactly where it is!
    m_odometry.update(
        Rotation2d.fromDegrees(-m_gyro.getAngle()), 
        leftEncoder.getPosition(), 
        rightEncoder.getPosition()
    );
  }

  // --- PATHPLANNER REQUIRED METHODS ---

  public Pose2d getPose() {
    return m_odometry.getPoseMeters(); 
  }

  public void resetPose(Pose2d pose) {
    m_odometry.resetPosition(
        Rotation2d.fromDegrees(-m_gyro.getAngle()), 
        leftEncoder.getPosition(), 
        rightEncoder.getPosition(), 
        pose
    );
  }

  public ChassisSpeeds getChassisSpeeds() {
    return m_kinematics.toChassisSpeeds(
        new DifferentialDriveWheelSpeeds(
            leftEncoder.getVelocity(), 
            rightEncoder.getVelocity()
        )
    );
  }

  public void driveRobotRelative(ChassisSpeeds speeds) {
    // Converts the desired speeds from PathPlanner into left and right wheel speeds
    DifferentialDriveWheelSpeeds wheelSpeeds = m_kinematics.toWheelSpeeds(speeds);
    
    // TO DO: Change this to your robot's actual max physical speed in meters per second
    double maxSpeedMPS = 4.56; 
    
    // Command the motors to spin at a percentage of their max speed (-1.0 to 1.0)
    leftLeader.set(wheelSpeeds.leftMetersPerSecond / maxSpeedMPS);
    rightLeader.set(wheelSpeeds.rightMetersPerSecond / maxSpeedMPS);
    m_drive.feed(); // Keeps the MotorSafety watchdog happy
  }

  public double getHeading() {
    return m_gyro.getAngle(); 
  }

  public void resetGyro() {
    m_gyro.reset();
  }

  public void arcadeDrive(double speed, double rotation) {
    m_drive.arcadeDrive(speed, rotation);
  }

  // Time + Gyro based Drive Straight for Autonomous
  public Command driveStraight(double speed, double seconds) {
    return Commands.runOnce(this::resetGyro) 
        .andThen(
            Commands.run(() -> {
                double error = getHeading(); 
                double kP = 0.07; // Correction strength
                this.arcadeDrive(speed, -error * kP); 
            }, this)
        )
        .withTimeout(seconds)
        .finallyDo(() -> this.arcadeDrive(0, 0)); // Safety stop
  }
}

