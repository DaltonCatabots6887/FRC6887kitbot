// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


/*
package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANFuelSubsystem;
import static frc.robot.Constants.FuelConstants.*;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
/*public class Elevator_Back extends Command {
  /** Creates a new Intake. */
/*
  CANFuelSubsystem fuelSubsystem;

  public Elevator_Back(CANFuelSubsystem fuelSystem) {
    addRequirements(fuelSystem);
    this.fuelSubsystem = fuelSystem;
  }

  // Called when the command is initially scheduled. Set the rollers to the
  // appropriate values for ejecting
  @Override
  public void initialize() {
    //fuelSubsystem
    //    .setelevatorRoller(SmartDashboard.getNumber("Elevator roller voltage value", ELEVATOR_VOLTAGE)); //CCW is positive
  }

  // Called every time the scheduler runs while the command is scheduled. This
  // command doesn't require updating any values while running
  @Override
  public void execute() {
    if (fuelSubsystem.isBottomSwitchPressed()) {
        fuelSubsystem.setelevatorRoller(0);
    } else {
        double voltage = SmartDashboard.getNumber("Elevator roller voltage value", ELEVATOR_VOLTAGE);
        fuelSubsystem.setelevatorRoller(voltage); // DOWN direction
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
    return fuelSubsystem.isBottomSwitchPressed();
  } */





package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANFuelSubsystem;
import static frc.robot.Constants.FuelConstants.*;

public class Elevator_Back extends Command {
    private final CANFuelSubsystem fuelSubsystem;
    // We stop at 1.0 instead of 0.0 so we don't slam the bottom
    //private final double BOTTOM_THRESHOLD = -100.0; 

    public Elevator_Back(CANFuelSubsystem fuelSystem) {
        this.fuelSubsystem = fuelSystem;
        addRequirements(fuelSystem);
    }
    /* 
    @Override
    public void execute() {
        double voltage = SmartDashboard.getNumber("Elevator Roller roller value", ELEVATOR_VOLTAGE);
        
        if (fuelSubsystem.getElevatorPosition() < BOTTOM_THRESHOLD) {
            fuelSubsystem.setelevatorRoller(0);RE
        } else {
            // Positive voltage moves it DOWN
            fuelSubsystem.setelevatorRoller(ELEVATOR_VOLTAGE);
        }
    }
*/

@Override
    public void execute() {
        // Use the voltage from SmartDashboard for easy tuning in the pits
        double voltage = SmartDashboard.getNumber("Elevator Roller roller value", ELEVATOR_VOLTAGE);
        
        fuelSubsystem.setelevatorRoller(1 * ELEVATOR_VOLTAGE);
      /* 
        if (fuelSubsystem.getElevatorPosition() >= TOP_THRESHOLD) {
            fuelSubsystem.setelevatorRoller(0);
        } else {
            // If -1 was UP in your previous setup, keep it here
            fuelSubsystem.setelevatorRoller(1 * BOTTOM_THRESHOLD); 
        }
    }
      */
    }

    public void initialize() {
    fuelSubsystem
        .setelevatorRoller(SmartDashboard.getNumber("Intaking Elevator value", ELEVATOR_VOLTAGE));
    fuelSubsystem.setelevatorRoller(SmartDashboard.getNumber("Intaking elevator roller value", ELEVATOR_VOLTAGE));
  }

    @Override
    public void end(boolean interrupted) {
        fuelSubsystem.setelevatorRoller(0);
    }

    @Override
    public boolean isFinished() {
      /* UNCOMMENT THIS TO STOP AUTOMATICALLY AT THE BOTTOM:
        return fuelSubsystem.getElevatorPosition() <= BOTTOM_THRESHOLD;
        */  
      return false;
    }
}



