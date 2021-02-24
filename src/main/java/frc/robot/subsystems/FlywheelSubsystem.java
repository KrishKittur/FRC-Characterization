package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FlywheelSubsystem extends SubsystemBase {

    private final CANSparkMax motor = new CANSparkMax(0, MotorType.kBrushless);
    private final CANEncoder encoder = motor.getEncoder();

    public double getRPM() {
        return encoder.getVelocity();
    }

    // Method to set the shooter by voltage
    public void set(double volts) {
        motor.setVoltage(volts);
    }

}
