package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.IncramentalTest;
import frc.robot.commands.SharpTest;
import frc.robot.commands.ZeroShooter;
import frc.robot.subsystems.FlywheelSubsystem;

// Robot Container
public class RobotContainer {

  // Variables
  private final XboxController controller = new XboxController(0);
  private final FlywheelSubsystem flywheel = new FlywheelSubsystem();

  // In the constructor configure the button bindings
  public RobotContainer() {
    configureButtonBindings();
  }

  // Method to configure the button bindings
  private void configureButtonBindings() {
    new JoystickButton(controller, Button.kA.value).whenPressed(
      new SequentialCommandGroup(
        new IncramentalTest(flywheel),
        new ZeroShooter(flywheel),
        new SharpTest(flywheel)
      )
    );
  }

  // Method to get the selected autonomus command
  public Command getAutonomousCommand() {
    return null;
  }
}
