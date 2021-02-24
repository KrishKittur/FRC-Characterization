package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FlywheelSubsystem;

public class ZeroShooter extends CommandBase {

    FlywheelSubsystem subsystem;

    public ZeroShooter(FlywheelSubsystem subsystem) {
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        subsystem.set(0.0);
    }

    @Override
    public boolean isFinished() {
        return (subsystem.getRPM() == 0.0);
    }

}
