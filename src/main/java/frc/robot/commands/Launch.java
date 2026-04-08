
package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANFuelSubsystem;
import static frc.robot.Constants.FuelConstants.*;

public class Launch extends Command {

  private final CANFuelSubsystem fuelSubsystem;

  public Launch(CANFuelSubsystem fuelSystem) {
    this.fuelSubsystem = fuelSystem;
    addRequirements(fuelSystem);
  }

  @Override
  public void initialize() {
    // We start the launcher wheels immediately so they begin spinning up
    double launcherVolt = SmartDashboard.getNumber("Launching launcher roller value", LAUNCHING_LAUNCHER_VOLTAGE);
    fuelSubsystem.setFeederRoller(launcherVolt);
  }

  @Override
  public void execute() {
    // 1. Grab fresh values every 20ms
    double currentVelocity = Math.abs(fuelSubsystem.getLauncherVelocity());
    double launcherVolt = SmartDashboard.getNumber("Launching launcher roller value", LAUNCHING_LAUNCHER_VOLTAGE);
    double feederVolt = SmartDashboard.getNumber("Launching feeder roller value", LAUNCHING_FEEDER_VOLTAGE);
    double rollerVolt = SmartDashboard.getNumber("Launching feeder roller value", LAUNCHING_ROLLERS_VOLTAGE);

    
    // 2. Logic: Only run feeder if launcher is fast enough
    if (currentVelocity >= velLimit) {
      // Launcher stays at high speed, Feeder turns ON
      fuelSubsystem.setIntakeLauncherRoller(LAUNCHING_LAUNCHER_VOLTAGE);
      fuelSubsystem.setFeederRoller(feederVolt);
      fuelSubsystem.setRollerRollers(rollerVolt);
    } else {
      // Launcher stays at high speed to keep revving, Feeder stays OFF
      fuelSubsystem.setIntakeLauncherRoller(LAUNCHING_LAUNCHER_VOLTAGE);
      fuelSubsystem.setFeederRoller(0);
    }
      
      //double RPM_PER_VOLT = 491.0;
      //double targetVelocity = (launcherVolt * RPM_PER_VOLT) - 40;
      //double dynamicVelLimit = targetVelocity * 0.90;
      
      /* 
      double dynamicVelLimit = ;
      if (currentVelocity >= dynamicVelLimit) {
              fuelSubsystem.setIntakeLauncherRoller(LAUNCHING_LAUNCHER_VOLTAGE);
      fuelSubsystem.setFeederRoller(feederVolt);
      //fuelSubsystem.setRollerRollers(rollerVolt);
    } else {
      // Launcher stays at high speed to keep revving, Feeder stays OFF
      fuelSubsystem.setIntakeLauncherRoller(LAUNCHING_LAUNCHER_VOLTAGE);
      fuelSubsystem.setFeederRoller(0);
      }
      */
    }
  /* 
    SmartDashboard.putBoolean("Shooter Ready", currentVelocity >= dynamicVelLimit);
    SmartDashboard.putNumber("Current Launcher RPM", currentVelocity);

    // Update the dashboard so Jose can see the status live
    SmartDashboard.putBoolean("Shooter Ready", currentVelocity >= dynamicVelLimit);
    SmartDashboard.putNumber("Current Launcher RPM", currentVelocity);*/
  

  @Override
  public void end(boolean interrupted) {
    // Stop everything when the trigger is released
    fuelSubsystem.stop();
  }

  @Override
  public boolean isFinished() {
    // While held, this command never "finishes" on its own
    return false;
  }
}
  
/* 
  package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANFuelSubsystem;
import static frc.robot.Constants.FuelConstants.*;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands 
public class Launch extends Command {
  /** Creates a new Intake. 

  private final CANFuelSubsystem fuelSubsystem;

  public Launch(CANFuelSubsystem fuelSystem) {
    this.fuelSubsystem = fuelSystem;
    addRequirements(fuelSystem);
  }

  // Called when the command is initially scheduled. Set the rollers to the
  // appropriate values for intaking
  @Override
  public void initialize() {
    /*fuelSubsystem.setIntakeLauncherRoller(
            SmartDashboard.getNumber("Launching launcher roller value", LAUNCHING_LAUNCHER_VOLTAGE));
    fuelSubsystem.setFeederRoller(SmartDashboard.getNumber("Launching feeder roller value", LAUNCHING_FEEDER_VOLTAGE));
    
    double launcherVolt = SmartDashboard.getNumber("Launching launcher roller value", LAUNCHING_LAUNCHER_VOLTAGE);
    double feederVolt = SmartDashboard.getNumber("Launching feeder roller value", LAUNCHING_FEEDER_VOLTAGE);

    // Keep launcher wheels going AND start the feeder
    fuelSubsystem.setIntakeLauncherRoller(launcherVolt);
    fuelSubsystem.setFeederRoller(feederVolt);
  }

  // Called every time the scheduler runs while the command is scheduled. This
  // command doesn't require updating any values while running
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted. Stop the rollers
  @Override
  public void end(boolean interrupted) {
    fuelSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
*/