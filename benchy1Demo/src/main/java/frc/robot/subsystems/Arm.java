package frc.robot.subsystems;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
// This is a special import that allows us to use the commands library
import static edu.wpi.first.wpilibj2.command.Commands.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Arm extends SubsystemBase{
    private final WPI_TalonFX armMotor = new WPI_TalonFX(Constants.IOConstants.talonFXPort);

    public Arm(){
        // Set up the arm motor
        armMotor.configFactoryDefault();
        armMotor.setSelectedSensorPosition(0, 0, 0);
        // Set the brake mode
        armMotor.setNeutralMode(Constants.ArmConstants.kNeutralMode);
        // Set up the encoder
        armMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, Constants.ArmConstants.kPIDLoopIdx, Constants.ArmConstants.kTimeoutMs);
        // Set the encoder phase
        armMotor.setSensorPhase(Constants.ArmConstants.kSensorPhase);
        // Set the direction of the motor
        armMotor.setInverted(Constants.ArmConstants.kMotorInvert);
        // Set the peak and nominal outputs. These are the values that the motor will be set to when the joystick is at its maximum value or normal value (Stopped)
        armMotor.configNominalOutputForward(0, Constants.ArmConstants.kTimeoutMs);
        armMotor.configNominalOutputReverse(0, Constants.ArmConstants.kTimeoutMs);
        armMotor.configPeakOutputForward(Constants.ArmConstants.kPeakOutput, Constants.ArmConstants.kTimeoutMs);
        armMotor.configPeakOutputReverse(-Constants.ArmConstants.kPeakOutput, Constants.ArmConstants.kTimeoutMs);
        // Configure the closed loop error, which is the range of error where the motor output is held constant
        armMotor.configAllowableClosedloopError(Constants.ArmConstants.kPIDLoopIdx, 0, Constants.ArmConstants.kTimeoutMs);
        // Set the PID gains in slot0
        armMotor.config_kF(Constants.ArmConstants.kPIDLoopIdx, Constants.ArmConstants.kF, Constants.ArmConstants.kTimeoutMs);
        armMotor.config_kP(Constants.ArmConstants.kPIDLoopIdx, Constants.ArmConstants.kP, Constants.ArmConstants.kTimeoutMs);
        armMotor.config_kI(Constants.ArmConstants.kPIDLoopIdx, Constants.ArmConstants.kI, Constants.ArmConstants.kTimeoutMs);
        armMotor.config_kD(Constants.ArmConstants.kPIDLoopIdx, Constants.ArmConstants.kD, Constants.ArmConstants.kTimeoutMs);

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
        return parallel(
            run(() -> {
                armMotor.set(ControlMode.PercentOutput,speed);
                updateShuffleboard(this::getArmVelocity, this::getArmPosition);
            }).withTimeout(2)
        ).withName("set arm speed");
    }

    public double getArmPosition(){
        return armMotor.getSelectedSensorPosition();
    }

    public double getArmVelocity(){
        return armMotor.getSelectedSensorVelocity();
    }

    public void resetArmPosition(){
        armMotor.setSelectedSensorPosition(0);
    }

    public void updateShuffleboard(DoubleSupplier curArmVel, DoubleSupplier curArmPos){
        Shuffleboard.update();
    }

    public CommandBase moveToBottomCommand(){
        return parallel (
            run(() -> {
                armMotor.set(ControlMode.Position, Constants.ArmConstants.kBottomPosition);
                updateShuffleboard(this::getArmVelocity, this::getArmPosition);
            }).withTimeout(5)
        ).withName("move arm to bottom");
    }

    public CommandBase moveToMiddleCommand(){
        return parallel (
            run(() -> {
                armMotor.set(ControlMode.Position, Constants.ArmConstants.kMiddlePosition);
                updateShuffleboard(this::getArmVelocity, this::getArmPosition);
            }).withTimeout(5)
        ).withName("move arm to middle");
    }

    public CommandBase moveToTopCommand(){
        return parallel (
            run(() -> {
                armMotor.set(ControlMode.Position, Constants.ArmConstants.kTopPosition);
                updateShuffleboard(this::getArmVelocity, this::getArmPosition);
            }).withTimeout(5)
        ).withName("move arm to top");
    }
}