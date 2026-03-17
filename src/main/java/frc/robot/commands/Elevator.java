 package frc.robot.commands;

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
        //double voltage = SmartDashboard.getNumber("Elevator Roller roller value", ELEVATOR_VOLTAGE);
        
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
