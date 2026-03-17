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


@Override
    public void execute() {
        // Use the voltage from SmartDashboard for easy tuning in the pits
        //double voltage = SmartDashboard.getNumber("Elevator Roller roller value", ELEVATOR_VOLTAGE);
        
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



