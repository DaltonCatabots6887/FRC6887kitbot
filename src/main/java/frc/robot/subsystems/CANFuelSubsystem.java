// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.FuelConstants.*;
import static frc.robot.Constants.OperatorConstants.DRIVE_SCALING;
import static frc.robot.Constants.OperatorConstants.ROTATION_SCALING;

import edu.wpi.first.wpilibj.DigitalInput;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkRelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;

public class CANFuelSubsystem extends SubsystemBase {
  private final SparkMax feederRoller;
  private final SparkMax intakeLauncherRoller;
  //private final SparkMax elevatorRoller;

  
  private final SparkMax m_elevatorMotor = new SparkMax(5, MotorType.kBrushless);
  private final RelativeEncoder m_elevatorEncoder = m_elevatorMotor.getEncoder();
  private final SparkClosedLoopController m_elevatorPID = m_elevatorMotor.getClosedLoopController();
  
  

  //Past Switch code- Jose
   
  private final DigitalInput m_topLimitSwitch = new DigitalInput(0);
  private final DigitalInput m_bottomLimitSwitch = new DigitalInput(1);
  
  public boolean isTopSwitchPressed() {
    // Returns true if hit. Use '!' if your switch logic is inverted.
    return !m_topLimitSwitch.get(); 
}

  public boolean isBottomSwitchPressed() {
    return !m_bottomLimitSwitch.get();
}
  
  /** Creates a new CANBallSubsystem. */
  public CANFuelSubsystem() {


    //intakeLauncherRoller = new SparkMax(INTAKE_LAUNCHER_MOTOR_ID, MotorType.kBrushless);
    intakeLauncherRoller = new SparkMax(INTAKE_LAUNCHER_MOTOR_ID, MotorType.kBrushless);
    feederRoller = new SparkMax(FEEDER_MOTOR_ID, MotorType.kBrushless);
    

    // Use the ID from Constants (3) for the elevator
    //elevatorRoller = new SparkMax(6, MotorType.kBrushless);

    // Setup Config
    SparkMaxConfig elevatorConfig = new SparkMaxConfig();
    elevatorConfig.smartCurrentLimit(ELEVATOR_LIMIT);
    elevatorConfig.closedLoop.p(0.1); // Jose's PID tuning
    
    // Apply config to the elevator
    m_elevatorMotor.configure(elevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Feeder Config
    SparkMaxConfig feederConfig = new SparkMaxConfig();
    feederConfig.smartCurrentLimit(FEEDER_MOTOR_CURRENT_LIMIT);
    feederRoller.configure(feederConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Launcher Config
    SparkMaxConfig launcherConfig = new SparkMaxConfig();
    launcherConfig.inverted(true);
    launcherConfig.smartCurrentLimit(LAUNCHER_MOTOR_CURRENT_LIMIT);
    intakeLauncherRoller.configure(launcherConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Zero the elevator (Make sure it's at the bottom on boot!)
    m_elevatorMotor.getEncoder().setPosition(0);

    // Dashboard tuning values
    SmartDashboard.putNumber("Intaking feeder roller value", INTAKING_FEEDER_VOLTAGE);
    SmartDashboard.putNumber("Intaking intake roller value", INTAKING_INTAKE_VOLTAGE);
    SmartDashboard.putNumber("Launching feeder roller value", LAUNCHING_FEEDER_VOLTAGE);
    SmartDashboard.putNumber("Launching launcher roller value", LAUNCHING_LAUNCHER_VOLTAGE);
    SmartDashboard.putNumber("Spin-up feeder roller value", SPIN_UP_FEEDER_VOLTAGE);
    SmartDashboard.putNumber("Spin-up launch time", SPIN_UP_SECONDS);
    SmartDashboard.putNumber("Elevator Roller roller value", ELEVATOR_VOLTAGE);
    SmartDashboard.putNumber("Speed", DRIVE_SCALING);
    SmartDashboard.putNumber("Rotation Speed", ROTATION_SCALING);



    /*
    // create brushed motors for each of the motors on the launcher mechanism
    intakeLauncherRoller = new SparkMax(INTAKE_LAUNCHER_MOTOR_ID, MotorType.kBrushless);
    feederRoller = new SparkMax(FEEDER_MOTOR_ID, MotorType.kBrushless);
    elevatorRoller = new SparkMax (ELEVATOR, MotorType.kBrushless);

    SparkMaxConfig elevatorConfig = new SparkMaxConfig();
    elevatorConfig.smartCurrentLimit(ELEVATOR_LIMIT);
    elevatorRoller.configure (elevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    // create the configuration for the feeder roller, set a current limit and apply
    // the config to the controller
    SparkMaxConfig feederConfig = new SparkMaxConfig();
    feederConfig.smartCurrentLimit(FEEDER_MOTOR_CURRENT_LIMIT);
    feederRoller.configure(feederConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // create the configuration for the launcher roller, set a current limit, set
    // the motor to inverted so that positive values are used for both intaking and
    // launching, and apply the config to the controller
    SparkMaxConfig launcherConfig = new SparkMaxConfig();
    launcherConfig.inverted(true);
    launcherConfig.smartCurrentLimit(LAUNCHER_MOTOR_CURRENT_LIMIT);
    intakeLauncherRoller.configure(launcherConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // put default values for various fuel operations onto the dashboard
    // all commands using this subsystem pull values from the dashbaord to allow
    // you to tune the values easily, and then replace the values in Constants.java
    // with your new values. For more information, see the Software Guide.
    SmartDashboard.putNumber("Intaking feeder roller value", INTAKING_FEEDER_VOLTAGE);
    SmartDashboard.putNumber("Intaking intake roller value", INTAKING_INTAKE_VOLTAGE);
    SmartDashboard.putNumber("Launching feeder roller value", LAUNCHING_FEEDER_VOLTAGE);
    SmartDashboard.putNumber("Launching launcher roller value", LAUNCHING_LAUNCHER_VOLTAGE);
    SmartDashboard.putNumber("Spin-up feeder roller value", SPIN_UP_FEEDER_VOLTAGE);
    SmartDashboard.putNumber("Elevator Roller roller value", ELEVATOR_VOLTAGE);

      //3-6 added for Rel. Elevator - Jose

        // 3. Configure PID (Proportional)
        // Start with 0.1. If it's too slow, increase to 0.2. 
        // If it shakes or slams, decrease it!
        elevatorConfig.closedLoop.p(0.1); 
        
        // 4. Optional: Set a Soft Limit so it can't crash into the ceiling
        // elevatorConfig.softLimit.forwardLimitEnabled(true).forwardLimit(90.0);

        // 5. Apply Config
        m_elevatorMotor.configure(elevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // 6. Set current position as ZERO (Make sure elevator is at bottom when starting!)
        m_elevatorEncoder.setPosition(0);

        */
  }


      public double getLauncherVelocity() {
      RelativeEncoder m_LaunchEncoder = feederRoller.getEncoder();
    // Replace 'm_launcherEncoder' with whatever you named your encoder object
    return m_LaunchEncoder.getVelocity(); 
}

  public double getElevatorPosition() {
    return m_elevatorMotor.getEncoder().getPosition();
  }

  // A method to set the voltage of the intake roller
  public void setIntakeLauncherRoller(double voltage) {
    intakeLauncherRoller.setVoltage(voltage);
  }

  // A method to set the voltage of the intake roller
  public void setFeederRoller(double voltage) {
    feederRoller.setVoltage(voltage);
  }

  public void setelevatorRoller(double voltage) {
    m_elevatorMotor.setVoltage(voltage);
  }

  // A method to stop the rollers
  public void stop() {
    intakeLauncherRoller.set(0);
    feederRoller.set(0);   
    m_elevatorMotor.set(0);
  }
  // Added for Rel. Elevator - Jose
  public void setElevatorPosition(double rotations) {
        m_elevatorPID.setReference(rotations, ControlType.kPosition);
    }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Elevator Pos", m_elevatorEncoder.getPosition()); //On smartdash to record top POS tmw - Jose
  }
}
