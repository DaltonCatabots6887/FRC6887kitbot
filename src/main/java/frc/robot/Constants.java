// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
  //I deleted drive constants on account of who cares.

  public static final class FuelConstants {
    // Motor controller IDs for Fuel Mechanism motors
    public static final int FEEDER_MOTOR_ID = 2;
    public static final int INTAKE_LAUNCHER_MOTOR_ID = 4;
    public static final int ROLLERS_MOTOR_ID = 5;
    
    //public static final int ELEVATOR = 5;

    // Current limit and nominal voltage for fuel mechanism motors.
    public static final int FEEDER_MOTOR_CURRENT_LIMIT = 40;
    public static int LAUNCHER_MOTOR_CURRENT_LIMIT = 40;
    public static final int ELEVATOR_LIMIT = 50;
    public static final int ROLLERS_MOTOR_CURRENT_LIMIT = 40;
    public static double velLimit = 3000;

    // Voltage values for various fuel operations. These values may need to be tuned
    // based on exact robot construction.             FEEDER ----> INDEX        LAUNCHER&INTAKE----->SHOOTER AXLE 
    // See the Software Guide for tuning information  
    public static final double ELEVATOR_VOLTAGE = 11;
    public static final double INTAKING_FEEDER_VOLTAGE = 12; //prev 12 <----Intake Axle(w/ agitators) //Make more powerful
    public static final double INTAKING_INTAKE_VOLTAGE = -9; //prev -6.5 <----Shooter Axle //Less than one above
    public static final double INTAKING_ROLLERS_VOLTAGE = 9; //black wheel intake axle (prev 7)       <-------------
    public static final double LAUNCHING_FEEDER_VOLTAGE = 8; //9 <----Intake Axle(w/ agitators)
    public static double LAUNCHING_LAUNCHER_VOLTAGE = 6.8; //10.6 <----Shooter Axle
    public static final double LAUNCHING_ROLLERS_VOLTAGE = 5;
    public static final double SPIN_UP_FEEDER_VOLTAGE = 6; //6  
    public static final double SPIN_UP_SECONDS = 0; //1
  }

  public static final class OperatorConstants {
    // Port constants for driver and operator controllers. These should match the
    // values in the Joystick tab of the Driver Station software
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final int OPERATOR_CONTROLLER_PORT = 0;

    // This value is multiplied by the joystick value when rotating the robot to
    // help avoid turning too fast and beign difficult to control
    public static final double DRIVE_SCALING = -.62;
    public static final double ROTATION_SCALING = -.62; //WAS .75 FOR DAVID

    //<3Hello! Loveyall:)Peace out!!
  }
}
