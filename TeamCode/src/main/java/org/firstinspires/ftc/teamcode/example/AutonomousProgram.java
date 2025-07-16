package org.firstinspires.ftc.teamcode.example;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;

import org.firstinspires.ftc.teamcode.example.subsystem.Claw;
import org.firstinspires.ftc.teamcode.example.subsystem.Extension;

@Autonomous(name = "NextFTC Autonomous Program Java")
public class AutonomousProgram extends NextFTCOpMode {
    public AutonomousProgram() {
        super(Extension.INSTANCE);
    }

    @Override
    public void onStartButtonPressed() {
        Extension.INSTANCE.toLow();
    }
}
