package frc.robot.subsystems;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
// This is a special import that allows us to use the commands library
import static edu.wpi.first.wpilibj2.command.Commands.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//Adding imports to reduce clutter
import frc.robot.Constants.*;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Arm extends SubsystemBase{
    private final WPI_TalonFX armMotor = new WPI_TalonFX(IOConstants.talonFXPort);

    public Arm(){
        // Set up the arm motor
        armMotor.configFactoryDefault();
        armMotor.setSelectedSensorPosition(0, 0, 0);
        // Set the brake mode
        armMotor.setNeutralMode(ArmConstants.kNeutralMode);
        // Set up the encoder
        armMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, ArmConstants.kPIDLoopIdx, ArmConstants.kTimeoutMs);
        // Set the encoder phase
        armMotor.setSensorPhase(ArmConstants.kSensorPhase);
        // Set the direction of the motor
        armMotor.setInverted(ArmConstants.kMotorInvert);
        // Set the peak and nominal outputs. These are the values that the motor will be set to when the joystick is at its maximum value or normal value (Stopped)
        armMotor.configNominalOutputForward(0, ArmConstants.kTimeoutMs);
        armMotor.configNominalOutputReverse(0, ArmConstants.kTimeoutMs);
        armMotor.configPeakOutputForward(ArmConstants.kPeakOutput, ArmConstants.kTimeoutMs);
        armMotor.configPeakOutputReverse(-ArmConstants.kPeakOutput, ArmConstants.kTimeoutMs);
        // Configure the closed loop error, which is the range of error where the motor output is held constant
        armMotor.configAllowableClosedloopError(ArmConstants.kPIDLoopIdx, 0, ArmConstants.kTimeoutMs);
        // Set the PID gains in slot0
        armMotor.config_kF(ArmConstants.kPIDLoopIdx, ArmConstants.kF, ArmConstants.kTimeoutMs);
        armMotor.config_kP(ArmConstants.kPIDLoopIdx, ArmConstants.kP, ArmConstants.kTimeoutMs);
        armMotor.config_kI(ArmConstants.kPIDLoopIdx, ArmConstants.kI, ArmConstants.kTimeoutMs);
        armMotor.config_kD(ArmConstants.kPIDLoopIdx, ArmConstants.kD, ArmConstants.kTimeoutMs);

        // Setup double suppliers for pos and vel
        DoubleSupplier curArmVel = this::getArmVelocity;
        DoubleSupplier curArmPos = this::getArmPosition;

        // Set up the shuffleboard
        Shuffleboard.getTab("Arm").addNumber("Arm Position", curArmPos);
        Shuffleboard.getTab("Arm").addNumber("Arm Velocity", curArmVel);

        // Set the default command for the arm. This should be the command that is run when no other commands are running
        setDefaultCommand(
            // Create a new command that will use the runOnce command constructor.
            runOnce(
                () -> {
                    //Stop the arm motor
                    armMotor.set(0);
                })
                // Give the command a name and 
                .andThen(run(() -> {})).withName("idle arm"));
    }

    public CommandBase setArmSpeed(double speed){
        // Create a new command that will use the parallel command constructor.
        return parallel(
            // Create a new command that will use the run command constructor.
            run(() -> {
                // Set the arm motor to the speed passed in
                armMotor.set(ControlMode.PercentOutput,speed);
                // Update the shuffleboard
                updateShuffleboard();
            }).withTimeout(2)
        ).withName("set arm speed");
    }

    public double getArmPosition(){
        // Return the current position of the arm in ticks
        return armMotor.getSelectedSensorPosition();
    }

    public double getArmVelocity(){
        // Return the current velocity of the arm in ticks per ?ms
        return armMotor.getSelectedSensorVelocity();
    }

    public void resetArmPosition(){
        // Reset the arm position to 0
        armMotor.setSelectedSensorPosition(0);
    }

    public void updateShuffleboard(){
        // Update the shuffleboard, any values that are passed in prior to the update will be updated, even if they are not used in the update method.
        Shuffleboard.update();
    }

    public CommandBase moveToBottomCommand(){
        // Create a new command that will use the parallel command constructor.
        return parallel (
            // Create a new command that will use the run command constructor.
            run(() -> {
                // Set the arm motor to the bottom position
                armMotor.set(ControlMode.Position, ArmConstants.kBottomPosition);
                // Update the shuffleboard
                updateShuffleboard();
            }).withTimeout(5)
        ).withName("move arm to bottom");
    }

    public CommandBase moveToMiddleCommand(){
        // Create a new command that will use the parallel command constructor.
        return parallel (
            // Create a new command that will use the run command constructor.
            run(() -> {
                // Set the arm motor to the middle position
                armMotor.set(ControlMode.Position, ArmConstants.kMiddlePosition);
                // Update the shuffleboard
                updateShuffleboard();
            }).withTimeout(5)
        ).withName("move arm to middle");
    }

    public CommandBase moveToTopCommand(){
        // Create a new command that will use the parallel command constructor.
        return parallel (
            // Create a new command that will use the run command constructor.
            run(() -> {
                // Set the arm motor to the top position
                armMotor.set(ControlMode.Position, ArmConstants.kTopPosition);
                // Update the shuffleboard
                updateShuffleboard();
            }).withTimeout(5)
        ).withName("move arm to top");
    }
}
