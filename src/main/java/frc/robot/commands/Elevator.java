/* 
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANFuelSubsystem;
import static frc.robot.Constants.FuelConstants.*;


/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands 
public class Elevator extends Command {
  /** Creates a new Intake. 

  CANFuelSubsystem fuelSubsystem;

  public Elevator(CANFuelSubsystem fuelSystem) {
    addRequirements(fuelSystem);
    this.fuelSubsystem = fuelSystem;
  }

  // Called when the command is initially scheduled. Set the rollers to the
  // appropriate values for ejecting
  @Override
  public void initialize() {
    //fuelSubsystem
    //    .setelevatorRoller(-1 * SmartDashboard.getNumber("Elevator roller voltage value", ELEVATOR_VOLTAGE)); //CCW is positive
  }

  // Called every time the scheduler runs while the command is scheduled. This
  // command doesn't require updating any values while running
  @Override
  public void execute() {
    if (fuelSubsystem.isTopSwitchPressed()) {
        fuelSubsystem.setelevatorRoller(0);
    } else {
        double voltage = SmartDashboard.getNumber("Elevator roller voltage value", ELEVATOR_VOLTAGE);
        fuelSubsystem.setelevatorRoller(-1 * voltage); // UP direction
    }
  }

  // Called once the command ends or is interrupted. Stop the rollers
  @Override
  public void end(boolean interrupted) {
    fuelSubsystem.setelevatorRoller(0);
  }

  // Returns true when the command should end.
  
  @Override
  public boolean isFinished() {
    return fuelSubsystem.isTopSwitchPressed();
  }
  
}
*//*
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANFuelSubsystem;
import static frc.robot.Constants.FuelConstants.*;

public class Elevator extends Command {
  private final CANFuelSubsystem fuelSubsystem;
  
  // Set your target rotations here (or pull from Constants)
  private final double TARGET_ROTATIONS = 75.0; 

  public Elevator(CANFuelSubsystem fuelSystem) {
    this.fuelSubsystem = fuelSystem;
    addRequirements(fuelSystem);
  }

  @Override
  public void initialize() {
    // Tell the SparkMax to go to the target position using the PID we set up earlier
    fuelSubsystem.setElevatorPosition(TARGET_ROTATIONS);
  }

  @Override
  public void execute() {
    // With Closed-Loop PID, the motor controller handles the movement.
    // We don't need to manually set voltages here anymore!
  }

  @Override
  public void end(boolean interrupted) {
    // Optional: If interrupted, you might want to keep holding position 
    // or stop. Usually, for an elevator, we leave it in PID mode so it doesn't fall.
  }

  @Override
  public boolean isFinished() {
    // The command is "finished" when the encoder is within 1 rotation of the target
    return Math.abs(fuelSubsystem.getElevatorPosition() - TARGET_ROTATIONS) < 1.0;
  }
  */








 package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANFuelSubsystem;
import static frc.robot.Constants.FuelConstants.*;

public class Elevator extends Command {
    private final CANFuelSubsystem fuelSubsystem;
    // Adjust this number based on your testing tomorrow!
    //private final double TOP_THRESHOLD = 1000.0; 

    public Elevator(CANFuelSubsystem fuelSystem) {
        this.fuelSubsystem = fuelSystem;
        addRequirements(fuelSystem);
    }

    @Override
    public void execute() {
        // Use the voltage from SmartDashboard for easy tuning in the pits
        double voltage = SmartDashboard.getNumber("Elevator Roller roller value", ELEVATOR_VOLTAGE);
        
        fuelSubsystem.setelevatorRoller(-1 * ELEVATOR_VOLTAGE);
        /* UNCOMMENT THIS FOR AUTO-STOP:
        if (fuelSubsystem.getElevatorPosition() >= TOP_THRESHOLD) {
            fuelSubsystem.setelevatorRoller(0);
        } else {
            fuelSubsystem.setelevatorRoller(-1 * ELEVATOR_VOLTAGE); 
        }
        */
    }
    @Override
    public void end(boolean interrupted) {
        fuelSubsystem.setelevatorRoller(0);
    }

    @Override
    public boolean isFinished() {
      /* UNCOMMENT THIS TO MAKE THE BUTTON STOP AUTOMATICALLY AT THE TOP:
        return fuelSubsystem.getElevatorPosition() >= TOP_THRESHOLD;
        */  
      return false;
    }
}
