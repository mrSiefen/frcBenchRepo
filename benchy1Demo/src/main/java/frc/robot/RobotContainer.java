// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import static edu.wpi.first.wpilibj2.command.Commands.parallel;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.subsystems.Arm;

public class RobotContainer {

  // The robot's subsystems and commands are defined here...
  private final Arm m_arm = new Arm();

  // The operator's joystick as a CommandJoystick. This is used to create commands that use the joystick
  CommandJoystick m_operJoystick = new CommandJoystick(Constants.IOConstants.operJoystickPort);


  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    m_operJoystick.button(Constants.ArmConstants.bottomPosButton).onTrue(m_arm.moveToBottomCommand());
    m_operJoystick.button(Constants.ArmConstants.middlePosButton).onTrue(m_arm.moveToMiddleCommand());
    m_operJoystick.button(Constants.ArmConstants.topPosButton).onTrue(m_arm.moveToTopCommand());

    // TODO: This is the part thats not working correctly.
    m_operJoystick.axisGreaterThan(Constants.IOConstants.armAxisNum, 0).onTrue(parallel(m_arm.setArmSpeed(m_operJoystick.getX())));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
