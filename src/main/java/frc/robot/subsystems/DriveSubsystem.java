/*
package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds; 

public class DriveSubsystem extends SubsystemBase {
  /*
  // 1. Declare all 4 motors. 
  // IMPORTANT: Change these numbers (1, 2, 3, 4) to match your REV Hardware Client IDs.
  private final SparkMax leftLeader = new SparkMax(11, MotorType.kBrushed);
  private final SparkMax leftFollower = new SparkMax(13, MotorType.kBrushed);
  
  private final SparkMax rightLeader = new SparkMax(12, MotorType.kBrushed);
  private final SparkMax rightFollower = new SparkMax(14, MotorType.kBrushed);
  
  private final ADIS16470_IMU m_gyro = new ADIS16470_IMU();

  public double getHeading() {
        return m_gyro.getAngle(); // Returns the current rotation in degrees
    }

    public void resetGyro() {
        m_gyro.reset();
    }

  // 2. The drive object ONLY needs the leaders.
  private final DifferentialDrive m_drive = new DifferentialDrive(leftLeader, rightLeader);

  public DriveSubsystem() {
    // Create a configuration object for the Spark Maxes
    SparkMaxConfig leftConfig = new SparkMaxConfig();
    SparkMaxConfig rightConfig = new SparkMaxConfig();
    
    // --- Right SIDE SETUP ---
    // Standard configuration for the leader
    rightLeader.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
    // Tell the left follower to do exactly what the leader (ID 1) does
    rightConfig.follow(rightLeader); 
    rightFollower.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // --- LEFT SIDE SETUP ---
    // We usually need to invert one side so "Forward" moves both sides the same way
    leftConfig.inverted(true);
    leftLeader.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
    // Tell the right follower to follow the right leader (ID 3)
    leftConfig.follow(leftLeader);
    leftFollower.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    /* 
    RobotConfig config;

    try {
      // 2. This looks at your PathPlanner GUI settings to get your robot's weight, etc.
      config = RobotConfig.fromGUISettings();
    } catch (Exception e) {
      // If it fails to load, it prints the error to the driver station
      e.printStackTrace();
      config = null; 
    }
    
    AutoBuilder.configure(
    this::getPose,                // Robot pose supplier (Requires Encoders!)
    this::resetPose,              // Method to reset odometry (Requires Encoders!)
    this::getChassisSpeeds,       // Current ChassisSpeeds supplier (Requires Encoders!)
    this::driveRobotRelative,     // Method that actually drives the robot
    new RamseteController(      // This is the "Non-Holonomic" one for Tank Drive
        2.0, 0.7                  // Standard B and Zeta values
    ),
    config,                       
    () -> false,                  
    this                      // Reference to this subsystem
    */

    
    
  /*  
    AutoBuilder.configure(
    () -> new Pose2d(), // Fake Pose
    (pose) -> {},       // Fake Reset
    () -> new ChassisSpeeds(), // Fake Speeds
    (speeds) -> this.arcadeDrive(speeds.vxMetersPerSecond, speeds.omegaRadiansPerSecond), 
    new PIDConstants(5.0, 0.0, 0.0),
    new PIDConstants(2.0, 0.0, 0.0),
    config,
    () -> false,
    this
);
*//*
  }

  /**
   * This is the method RobotContainer calls to move the robot.
   * @param speed Forward/Backward (usually -1.0 to 1.0)
   * @param rotation Left/Right turn (usually -1.0 to 1.0)
   *//*
  public void arcadeDrive(double speed, double rotation) {
    m_drive.arcadeDrive(speed, rotation);
  }

  @Override
  public void periodic() {
    // This runs every 20ms. You can add SmartDashboard telemetry here later.
  }

  // Fake Pose for code that doesn't have encoders
  public Pose2d getPose() {
    return new Pose2d(); 
  }

  // Fake Reset
  public void resetPose(Pose2d pose) {}

  // Fake Speeds
  public ChassisSpeeds getChassisSpeeds() {
    return new ChassisSpeeds();
  }

  // Actual Drive helper for PathPlanner
  public void driveRobotRelative(ChassisSpeeds speeds) {
    this.arcadeDrive(speeds.vxMetersPerSecond, speeds.omegaRadiansPerSecond);
}

  public Command driveStraight(double speed, double seconds) {
    return Commands.runOnce(this::resetGyro) // Start by zeroing the gyro
        .andThen(
            Commands.run(() -> {
                double error = getHeading(); // How far have we drifted from 0.0?
                double kP = 0.03;            // Correction strength (tuning constant)       <-------Commands autonomous drive straight example
                this.arcadeDrive(speed, -error * kP); // If error is positive (drifted right), -error*kP will turn us slightly left to compensate. 
            }, this)
        )
        .withTimeout(seconds)
        .finallyDo(() -> this.arcadeDrive(0, 0)); // Stop the robot at the end
}*/

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class DriveSubsystem extends SubsystemBase {
  
  // 1. Declare motors (kBrushed is correct for CIMs)
  private final SparkMax leftLeader = new SparkMax(11, MotorType.kBrushless);
  private final SparkMax leftFollower = new SparkMax(13, MotorType.kBrushless);
  
  private final SparkMax rightLeader = new SparkMax(12, MotorType.kBrushless);
  private final SparkMax rightFollower = new SparkMax(14, MotorType.kBrushless);
  
  private final ADIS16470_IMU m_gyro = new ADIS16470_IMU();
  private final DifferentialDrive m_drive = new DifferentialDrive(leftLeader, rightLeader);

  public DriveSubsystem() {
    SparkMaxConfig leftConfig = new SparkMaxConfig();
    SparkMaxConfig rightConfig = new SparkMaxConfig();
    
    // --- RIGHT SIDE SETUP ---
    rightLeader.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightConfig.follow(rightLeader); 
    rightFollower.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // --- LEFT SIDE SETUP ---
    leftConfig.inverted(true); // Invert left side so forward is forward
    leftLeader.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    leftConfig.follow(leftLeader);
    leftFollower.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
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
                double kP = 0.03; // Correction strength
                this.arcadeDrive(speed, -error * kP); 
            }, this)
        )
        .withTimeout(seconds)
        .finallyDo(() -> this.arcadeDrive(0, 0)); // Safety stop
  }
}

