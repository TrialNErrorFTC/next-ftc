package org.firstinspires.ftc.teamcode.example.subsystem;

import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;

public class Angle extends Subsystem {
    // BOILERPLATE
    public static final Angle INSTANCE = new Angle();
    private Angle() { }

    // USER CODE
    public MotorEx motorAngleRight;
    public MotorEx motorAngleLeft;
    public PIDFController controller = new PIDFController(10, 0.049988, 0.0, new StaticFeedforward(0.0));
    MotorGroup angleMotorGroup = new MotorGroup(motorAngleRight, motorAngleLeft);


    @Override
    public void initialize() {
        motorAngleLeft = new MotorEx("motorAngleLeft");
        motorAngleRight = new MotorEx("motorAngleRight");
    }
}
