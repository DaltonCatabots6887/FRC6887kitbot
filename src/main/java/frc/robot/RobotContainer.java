// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


/*
package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.CANFuelSubsystem;
import frc.robot.commands.*;
import static frc.robot.Constants.OperatorConstants.*;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import static frc.robot.Constants.FuelConstants.*;
import com.pathplanner.lib.auto.NamedCommands;



public class RobotContainer {
    
    private final CANFuelSubsystem m_fuelSubsystem;
    

    // Subsystems
    private final DriveSubsystem m_drive = new DriveSubsystem();
    private final CANFuelSubsystem m_fuel = new CANFuelSubsystem();

    private final SendableChooser<Command> autoChooser;

    // Controllers
    private final CommandXboxController driverController = new CommandXboxController(DRIVER_CONTROLLER_PORT);
    private final CommandXboxController operatorController = new CommandXboxController(OPERATOR_CONTROLLER_PORT);


            


        public RobotContainer(){
       // Make sure you have your subsystem initialized
    m_fuelSubsystem = new CANFuelSubsystem();
        
    
    // Register the named command by creating a new 'Eject' object
    /*NamedCommands.registerCommand("Intake", new Eject(m_fuelSubsystem));
   
    NamedCommands.registerCommand("AutoShoot", 
    new SpinUp(m_fuel)
        .withTimeout(SPIN_UP_SECONDS)
        .andThen(new Launch(m_fuel)));

    // Elevator going up for 1.5 seconds
    NamedCommands.registerCommand("ElevatorUp", 
    new Elevator(m_fuel).withTimeout(1.5)
    );

    // Elevator going down for 1.5 seconds
    NamedCommands.registerCommand("ElevatorDown", 
    new Elevator_Back(m_fuel).withTimeout(1.5)
    );  
    *//*
    

    public Command getAutonomousCommand() {

   configureBindings();

 

   autoChooser = AutoBuilder.buildAutoChooser();

   // These are your manual code autos
    autoChooser.addOption("Left Side Auto", LeftAuto());
    autoChooser.addOption("Right Side Auto", RightAuto());

    // Set the one you use most as the Default
    autoChooser.setDefaultOption("Middle Auto", MiddleAuto());
        // 3. PUT it on the dashboard
        SmartDashboard.putData("Auto Chooser", autoChooser);
    //return null; 
    
};
}       

         
  
        
        
        

    // ... in RobotContainer constructor
    //NamedCommands.registerCommand("Shoot", Shoot);

    

    private void configureBindings() {
        // DRIVER: Arcade Drive
        // Left Y stick = Forward/Back, Right X stick = Rotation
        m_drive.setDefaultCommand(
            Commands.run(
                () -> m_drive.arcadeDrive(
                    -driverController.getLeftY() * DRIVE_SCALING, 
                    -driverController.getLeftX() * ROTATION_SCALING
                ),
                m_drive
            )
        );

        // OPERATOR: Intake, Launch, and Eject
        
        // Hold Left Bumper to Intake
        operatorController.leftTrigger().whileTrue(new Eject(m_fuel));

        // Hold Right Bumper for the full Launch Sequence (Spin up then Feed)
        operatorController.rightTrigger().whileTrue(
            new SpinUp(m_fuel).withTimeout(SPIN_UP_SECONDS)
                .andThen(new Launch(m_fuel))
        )        
        .onFalse(
            Commands.runOnce(m_fuel::stop,m_fuel)
        );
        

        // Hold A to Eject (Reverse motors)
        operatorController.a().whileTrue(new Intake(m_fuel));

        // ELEVATOR UP: Hold Right Bumper
        // It will automatically stop if it hits the Top Limit Switch
        operatorController.rightBumper()
        .whileTrue(new Elevator(m_fuel));

        // ELEVATOR DOWN: Hold Left Bumper
        // It will automatically stop if it hits the Bottom Limit Switch
        operatorController.leftBumper()
        .whileTrue(new Elevator_Back(m_fuel));

        //Mech for Rel Elevator - Jose
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
        
        //operatorController.rightBumper().and(() -> !m_fuel.isBumperPressed()).whileTrue(new Elevator(m_fuel));
        //operatorController.leftBumper().whileTrue(new Elevator_Back(m_fuel));
        *//*
    }
    /* 
    public Command getAutonomousCommand() {
        // Simple Auto: Drive forward at 50% power for 2 seconds
        return Commands.run(() -> m_drive.arcadeDrive(0.5, 0), m_drive)
                       .withTimeout(2.0)
                       .andThen(() -> m_drive.arcadeDrive(0, 0));
            *//* 
    public Command MiddleAuto() {
        
        /* -pathplanner-
        return new PathPlannerAuto("Middle Middle B");
        return autoChooser.getSelected();
        
        -previous auto code-

        return Commands.sequence(
        // 1. Drive straight for 3 seconds at 50% speed
        m_drive.driveStraight(0.5, 3.0),
        
        // 2. Wait a split second
        Commands.waitSeconds(0.5),
        
        // 3. Shoot your fuel
        new SpinUp(m_fuel).withTimeout(SPIN_UP_SECONDS),
        new Launch(m_fuel).withTimeout(1.0),
        
        // 4. Stop everything
        Commands.runOnce(m_fuel::stop, m_fuel)



        
        Gemini #1

        return Commands.sequence(
        // 1. SHOOT: Spin up and launch (Timed for 3 seconds total)
        new SpinUp(m_fuel).withTimeout(1.0),
        new Launch(m_fuel).withTimeout(2.0),
        Commands.runOnce(m_fuel::stop, m_fuel),

        // 2. LIFT: Move elevator UP to clear the climber hooks
        // This stops automatically when it hits your Top Limit Switch
        new Elevator(m_fuel), 

        // 3. DRIVE: Move 100 inches (Roughly 2.5 - 3.5 seconds at half speed)
        // You will need to tune this 'seconds' value on the carpet!
        m_drive.driveStraight(0.5, 3.0), 

        // 4. CLIMB: Lower the elevator to hook the ladder and lift the robot
        // This stops automatically when it hits your Bottom Limit Switch
        new Elevator_Back(m_fuel)

        *//*

        //Gemini #2


        return Commands.sequence(
        // --- STEP 1: SHOOT INTO THE HUB ---
        Commands.print("Auton: Starting Shoot..."),
        new SpinUp(m_fuel).withTimeout(1.0),    // Let motors reach speed
        new Launch(m_fuel).withTimeout(1.5),    // Feed the fuel into shooter
        Commands.runOnce(m_fuel::stop, m_fuel), // Turn off shooter motors

        // --- STEP 2: PREPARE CLIMBER ---
        Commands.print("Auton: Raising Elevator..."),
        new Elevator(m_fuel), // Runs until Top Limit Switch is hit

        // --- STEP 3: DRIVE TO LADDER (100 inches) ---
        Commands.print("Auton: Driving to Ladder..."),
        // Note: 3.5 seconds is a guess for 100 inches at half speed. 
        // Increase this if you stop short!
        m_drive.driveStraight(0.5, 3.5), 

        // --- STEP 4: CLIMB ---
        Commands.print("Auton: Hooking and Lifting..."),
        new Elevator_Back(m_fuel) // Runs until Bottom Limit Switch is hit
    );
    }
    public Command LeftAuto() {
        return Commands.sequence(
            new SpinUp(m_fuel).withTimeout(1.0),
            new Launch(m_fuel).withTimeout(1.5),
            Commands.runOnce(m_fuel::stop, m_fuel),
            Commands.run(() -> m_drive.arcadeDrive(0, 0.4), m_drive)
                    .until(() -> Math.abs(m_drive.getHeading()) >= 178) 
                    .withTimeout(1.5),
            new Elevator(m_fuel).withTimeout(0.5),
            m_drive.driveStraight(0.5, 3.0),
            new Elevator_Back(m_fuel)
        );
    }

    public Command RightAuto() {
        return Commands.sequence(
            new SpinUp(m_fuel).withTimeout(1.0),
            new Launch(m_fuel).withTimeout(1.5),
            Commands.runOnce(m_fuel::stop, m_fuel),
            Commands.run(() -> m_drive.arcadeDrive(0, -0.4), m_drive)
                    .until(() -> Math.abs(m_drive.getHeading()) >= 178)
                    .withTimeout(1.5),
            new Elevator(m_fuel).withTimeout(0.5),
            m_drive.driveStraight(0.5, 3.0),
            new Elevator_Back(m_fuel)
        );
    }
*/

/*
//______ _____   _____ 
 |  ____|  __ \ / ____|
 | |__  | |__) | |     
 |  __| |  _  /| |     
 | |    | | \ \| |____ 
 |_|    |_|  \_\\_____|
 */



package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.CANFuelSubsystem;
import frc.robot.commands.*;
import static frc.robot.Constants.OperatorConstants.*;
import static frc.robot.Constants.FuelConstants.*;

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
        // Setup Auto Chooser
        autoChooser.addOption("Left Side Auto", LeftAuto());
        autoChooser.addOption("Right Side Auto", RightAuto());
        autoChooser.setDefaultOption("Middle Auto", MiddleAuto());

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
                    -driverController.getLeftY() * DRIVE_SCALING, 
                    -driverController.getLeftX() * ROTATION_SCALING
                ),
                m_drive
            )
        );

        // OPERATOR: Intake, Launch, and Eject
        operatorController.leftTrigger().whileTrue(new Eject(m_fuel));

        operatorController.rightTrigger().whileTrue(
            new SpinUp(m_fuel).withTimeout(SPIN_UP_SECONDS)
                .andThen(new Launch(m_fuel))
        ).onFalse(
            Commands.runOnce(m_fuel::stop, m_fuel)
        );

        operatorController.a().whileTrue(new Intake(m_fuel));

        // ELEVATOR CONTROLS
        operatorController.povDown().whileTrue(new Elevator(m_fuel));
        operatorController.povUp().whileTrue(new Elevator_Back(m_fuel));
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
            Commands.print("Auton: Starting Shoot..."),
            new SpinUp(m_fuel).withTimeout(1.0),
            new Launch(m_fuel).withTimeout(5),
            m_drive.driveStraight(0.5,0.5),
            m_drive.driveStraight(-0.5,0.5),
            new SpinUp(m_fuel).withTimeout(1.0),
            new Launch(m_fuel).withTimeout(5),
            m_drive.driveStraight(0.5,0.5),
            m_drive.driveStraight(-0.5,0.5),
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
            
            new Elevator(m_fuel).withTimeout(1.0),
            m_drive.driveStraight(0.5, 3.0),
            new Elevator_Back(m_fuel).withTimeout(1.0)
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
            
            new Elevator(m_fuel).withTimeout(1.0),
            m_drive.driveStraight(0.5, 3.0),
            new Elevator_Back(m_fuel).withTimeout(1.0)
        );
    }
}







        
    