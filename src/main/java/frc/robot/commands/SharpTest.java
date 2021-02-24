package frc.robot.commands;

import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FlywheelSubsystem;

public class SharpTest extends CommandBase {

    Timer timer;
    double appVoltage = 12.0 * 0.6;
    FlywheelSubsystem subsystem;

    public SharpTest(FlywheelSubsystem subsystem) {
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();
        subsystem.set(appVoltage);
        try {
            FileWriter writer = new FileWriter(Filesystem.getDeployDirectory() + "/sharp.csv");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute() {
        try {
            FileWriter writer = new FileWriter(Filesystem.getDeployDirectory() + "/incramental.csv", true);
            writer.write(subsystem.getRPM() + ", " + appVoltage + ", " + timer.get() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isFinished() {
        return (timer.get() >= 10.0);
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.set(0.0);
    }
    
}
