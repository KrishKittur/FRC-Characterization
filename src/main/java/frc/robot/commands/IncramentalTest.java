package frc.robot.commands;

import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FlywheelSubsystem;


public class IncramentalTest extends CommandBase {

    Timer timer;
    double volts = 0.0;
    FlywheelSubsystem subsystem;
    
    public IncramentalTest(FlywheelSubsystem subsystem) {
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();
        try {
            FileWriter writer = new FileWriter(Filesystem.getDeployDirectory() + "/incramental.csv");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute() {
        subsystem.set(volts);
        volts += 0.002;
        try {
            FileWriter writer = new FileWriter(Filesystem.getDeployDirectory() + "/incramental.csv", true);
            writer.write(subsystem.getRPM() + ", " + volts + ", " + timer.get() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isFinished() {
        return (volts >= 12.0);
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.set(0.0);
    }
    
}
