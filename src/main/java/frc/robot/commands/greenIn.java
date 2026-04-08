package frc.robot.commands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANFuelSubsystem;
import static frc.robot.Constants.FuelConstants.*;

public class greenIn extends Command {
    CANFuelSubsystem fuelSubsystem;

  public greenIn(CANFuelSubsystem fuelSystem) {
    addRequirements(fuelSystem);
    this.fuelSubsystem = fuelSystem;
  }

@Override
  public void initialize() {
    fuelSubsystem
        .setFeederRoller(SmartDashboard.getNumber("Intaking feeder roller value", INTAKING_FEEDER_VOLTAGE));
  }

  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted. Stop the rollers
  @Override
  public void end(boolean interrupted) {
    fuelSubsystem.setFeederRoller(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}
