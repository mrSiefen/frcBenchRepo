package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Constants {
    public static final class IOConstants {
        public static final int driverJoyPort = 0;
        public static final int talonFXPort = 1;
        public static final int talonSRXPort = 0;
        public static final int pigeonPort = 0;
        public static final int pcmPort = 0;
        public static final int pdpPort = 0;
        public static int operJoystickPort = 1;
        public static int armAxisNum = 1;
    }

    public static final class ArmConstants {
        // PID gains may have to be adjusted based on the responsiveness of control loop.
        public static final int kSlotIdx = 0;
        // Talon FX supports multiple (cascaded) PID loops. For now we just want the primary one.
        public static final int kPIDLoopIdx = 0;
        // The timeout for the PID loops
        public static final int kTimeoutMs = 30;
        // Sets the direction of the sensor. True sets the sensor to positive when outputting
        public static final boolean kSensorPhase = true;
        // Invert motor or not
        public static boolean kMotorInvert = false;
        // Brake or coast the motor
        public static NeutralMode kNeutralMode = NeutralMode.Brake;
        public static double kBottomPosition = 0;
        public static double kMiddlePosition = 25000;
        public static double kTopPosition = 50000;
        public static int bottomPosButton = 12;
        public static double armAxisThreshold = 0.1;
        public static int middlePosButton = 10;
        public static int topPosButton = 8;

        // PID gains may have to be adjusted based on the responsiveness of control loop. PID is used to correct sensor input.
        /* It is composed of three terms: proportional, integral, and derivative.
        * The proportional term is the error multiplied by a constant.
        * The integral term is the sum of the errors multiplied by a constant.
        * The derivative term is the change in error multiplied by a constant.
        * The sum of these three terms is the output of the PID loop.
        * The constants are called the gains.
        */

        // kp is the proportional gain, which is used to correct sensor input
        public static final double kP = 0.1;
        // ki is the integral gain, which is used to correct steady state errors
        public static final double kI = 0;
        // kd is the derivative gain, which is used to reduce the amount of overshoot
        public static final double kD = 0;
        // kf is the feed forward gain, which is used to calculate the output of the motor
        public static final double kF = 0;
        // kIzone is the integral zone, which is the range of error where the integral gain
        public static final int kIzone = 0;
        // kPeakOutput is the maximum output of the PID loop, used to set the limit of the motor
        public static final double kPeakOutput = 0.05;
    }
}
