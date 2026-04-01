package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.CANFuelSubsystem;
import frc.robot.Constants.FuelConstants;
import frc.robot.commands.*;
import static frc.robot.Constants.OperatorConstants.*;
import static frc.robot.Constants.FuelConstants.*;

// PathPlanner
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

public class RobotContainer {
    
    // Subsystems
    private final DriveSubsystem m_drive = new DriveSubsystem();
    private final CANFuelSubsystem m_fuel = new CANFuelSubsystem();

    // Chooser needs to be instantiated here
    private final SendableChooser<Command> autoChooser = new SendableChooser<>();

    // Controllers
    private final CommandXboxController driverController = new CommandXboxController(DRIVER_CONTROLLER_PORT);
    private final CommandXboxController operatorController = new CommandXboxController(OPERATOR_CONTROLLER_PORT);

    public RobotContainer() {
        // 1. REGISTER COMMANDS FIRST
        // The String (e.g., "AutoShoot") MUST match what you type in the PathPlanner App exactly.
        NamedCommands.registerCommand("AutoShoot", 
            new SpinUp(m_fuel).withTimeout(SPIN_UP_SECONDS)
                .andThen(new Launch(m_fuel).withTimeout(2.0))
        );

        NamedCommands.registerCommand("Intake", new In(m_fuel));
    
        //NamedCommands.registerCommand("ElevatorUp", new Elevator_Back(m_fuel).withTimeout(1.5));
    
        //NamedCommands.registerCommand("ElevatorDown", new Elevator(m_fuel).withTimeout(1.5));
        
        // Setup Auto Chooser
        autoChooser.addOption("Left Side Auto", LeftAuto());
        autoChooser.addOption("Right Side Auto", RightAuto());
        autoChooser.setDefaultOption("Middle Auto", MiddleAuto());
        autoChooser.addOption("MOBA", new PathPlannerAuto("MOBA"));
        autoChooser.addOption("MM Auto", new PathPlannerAuto("MM Auto"));
        autoChooser.addOption("Curve Test", new PathPlannerAuto("C Test"));
        autoChooser.addOption("Straight Test", new PathPlannerAuto("S Test"));
        autoChooser.addOption("Right Right B", new PathPlannerAuto("Right Right B"));
        autoChooser.addOption("Test", new PathPlannerAuto("Test"));
        
        // Put chooser on dashboard
        SmartDashboard.putData("Auto Chooser", autoChooser);

        // Configure controller buttons
        configureBindings();
    }

    private void configureBindings() {
        // DRIVER: Arcade Drive
        
        m_drive.setDefaultCommand(
        Commands.run(
            () -> m_drive.arcadeDrive(
                // Apply a 10% deadband to the Forward/Back axis
                -MathUtil.applyDeadband(driverController.getLeftY(), 0.03) * DRIVE_SCALING, 
                // Apply a 10% deadband to the Rotation axis
                -MathUtil.applyDeadband(driverController.getLeftX(), 0.03) * ROTATION_SCALING
            ),
            m_drive
        )
    );

        // OPERATOR: Intake, Launch, and Eject
        operatorController.leftTrigger().whileTrue(new In(m_fuel));

        operatorController.rightTrigger().whileTrue(
            new SpinUp(m_fuel).withTimeout(SPIN_UP_SECONDS)
                .andThen(new Launch(m_fuel))
        ).onFalse(
            Commands.runOnce(m_fuel::stop, m_fuel)
        );

        operatorController.a().whileTrue(new Out(m_fuel));
        operatorController.b().whileTrue(new OutIn(m_fuel));

        // ELEVATOR CONTROLS
        
        //operatorController.povDown().whileTrue(new Elevator(m_fuel));
        //operatorController.povUp().whileTrue(new Elevator_Back(m_fuel));
        //operatorController.povUp().onTrue(Commands.runOnce(()-> FuelConstants.velLimit += 1000).alongWith(Commands.runOnce(()-> FuelConstants.LAUNCHING_LAUNCHER_VOLTAGE += .8)));
        //operatorController.povDown().onTrue(Commands.runOnce(()-> FuelConstants.velLimit -= 1000).alongWith(Commands.runOnce(()-> FuelConstants.LAUNCHING_LAUNCHER_VOLTAGE -= .8)));
        operatorController.y().onTrue(Commands.runOnce(()-> FuelConstants.velLimit = 3000).alongWith(Commands.runOnce(()-> FuelConstants.LAUNCHING_LAUNCHER_VOLTAGE = 7.6)));;
        operatorController.povUp().onTrue(Commands.runOnce(()-> FuelConstants.LAUNCHING_LAUNCHER_VOLTAGE += .4));
        operatorController.povDown().onTrue(Commands.runOnce(()-> FuelConstants.LAUNCHING_LAUNCHER_VOLTAGE += -.4));

        /* 
        double BOTTOM_ROTATIONS = 0.0;
        double TOP_ROTATIONS = 75.0; //TEST THIS TOMMOROW - Jose

// Left Bumper -> Snap to BOTTOM
operatorController.leftBumper().onTrue(
    Commands.runOnce(() -> m_fuel.setElevatorPosition(BOTTOM_ROTATIONS), m_fuel)
);

// Right Bumper -> Snap to TOP
operatorController.rightBumper().onTrue(
    Commands.runOnce(() -> m_fuel.setElevatorPosition(TOP_ROTATIONS), m_fuel)
);
*/
    }

    // Called by Robot.java when Auto starts
    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }

    // --- AUTONOMOUS ROUTINES ---

    public Command MiddleAuto() {
        return Commands.sequence(
            m_drive.driveStraight(-0.3,2),
            Commands.print("Auton: Starting Shoot..."),
            new SpinUp(m_fuel).withTimeout(1.0),
            new Launch(m_fuel).withTimeout(5),
            m_drive.driveStraight(-0.6,0.4),
            m_drive.driveStraight(0.0,0.2),
            m_drive.driveStraight(0.6,0.4),
            new SpinUp(m_fuel).withTimeout(1.0),
            new Launch(m_fuel).withTimeout(5),
            m_drive.driveStraight(-0.6,0.4),
            m_drive.driveStraight(0.0,0.2),
            m_drive.driveStraight(0.6,0.4),
            new SpinUp(m_fuel).withTimeout(1.0),
            new Launch(m_fuel).withTimeout(5),
            Commands.runOnce(m_fuel::stop, m_fuel)
            /*
            Commands.print("Auton: Raising Elevator..."),
            new Elevator(m_fuel).withTimeout(2.0), // Timeout added for safety

            Commands.print("Auton: Driving to Ladder..."),
            m_drive.driveStraight(0.5, 3.5), 

            Commands.print("Auton: Hooking and Lifting..."),
            new Elevator_Back(m_fuel).withTimeout(2.0) // Timeout added for safety
            */
        );
    }

    public Command LeftAuto() {
        return Commands.sequence(
            new SpinUp(m_fuel).withTimeout(1.0),
            new Launch(m_fuel).withTimeout(1.5),
            Commands.runOnce(m_fuel::stop, m_fuel),
            
            Commands.run(() -> m_drive.arcadeDrive(0, 0.4), m_drive)
                    .until(() -> Math.abs(m_drive.getHeading()) >= 178) 
                    .withTimeout(2.0), // Failsafe timeout
            
            //new Elevator(m_fuel).withTimeout(1.0),
            m_drive.driveStraight(0.5, 3.0)
            //new Elevator_Back(m_fuel).withTimeout(1.0)
        );
    }

    public Command RightAuto() {
        return Commands.sequence(
            new SpinUp(m_fuel).withTimeout(1.0),
            new Launch(m_fuel).withTimeout(1.5),
            Commands.runOnce(m_fuel::stop, m_fuel),
            
            Commands.run(() -> m_drive.arcadeDrive(0, -0.4), m_drive)
                    .until(() -> Math.abs(m_drive.getHeading()) >= 178)
                    .withTimeout(2.0), // Failsafe timeout
            
            //new Elevator(m_fuel).withTimeout(1.0),
            m_drive.driveStraight(0.5, 3.0)
            //new Elevator_Back(m_fuel).withTimeout(1.0)
        );
    }
}







        
    