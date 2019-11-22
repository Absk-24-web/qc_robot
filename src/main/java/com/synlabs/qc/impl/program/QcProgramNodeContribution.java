package com.synlabs.qc.impl.program;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.domain.userinteraction.robot.movement.MovementCompleteEvent;
import com.ur.urcap.api.domain.userinteraction.robot.movement.MovementErrorEvent;
import com.ur.urcap.api.domain.userinteraction.robot.movement.RobotMovement;
import com.ur.urcap.api.domain.userinteraction.robot.movement.RobotMovementCallback;
import com.ur.urcap.api.domain.value.Pose;
import com.ur.urcap.api.domain.value.ValueFactoryProvider;
import com.ur.urcap.api.domain.value.simple.Angle;
import com.ur.urcap.api.domain.value.simple.Length;

public class QcProgramNodeContribution implements ProgramNodeContribution {
    private static final String CENTER_POSITION = "center_pose";
    private final RobotMovement robotMovement;
    private final DataModel model;
    private final ProgramAPIProvider apiProvider;
    private QcProgramNodeView view;
    private URCapAPI urCapAPI;

    public QcProgramNodeContribution(ProgramAPIProvider apiProvider, QcProgramNodeView view, DataModel model) {
        this.model = model;
        this.apiProvider = apiProvider;
        robotMovement = apiProvider.getUserInterfaceAPI().getUserInteraction().getRobotMovement();
    }


    public void moveRobot() {
        //clearErrors();
        Pose centerPose = model.get(CENTER_POSITION, (Pose) null);
        if (centerPose != null) {
            robotMovement.requestUserToMoveRobot(centerPose, new RobotMovementCallback() {

                @Override
                public void onComplete(MovementCompleteEvent event) {

                }

                @Override
                public void onError(MovementErrorEvent event) {
                    // updateError(new EllipseState(getErrorMessage(event.getErrorType())));
                }
            });
        }
    }

    private Pose createPoseUsingCenterPoseAndOffset(Pose pose, double xOffset, double yOffset, double zOffset,
                                                    Length.Unit unit) {
        double x = pose.getPosition().getX(unit) + xOffset;
        double y = pose.getPosition().getY(unit) + yOffset;
        double z = pose.getPosition().getZ(unit) + zOffset;
        double rx = pose.getRotation().getRX(Angle.Unit.RAD);
        double ry = pose.getRotation().getRY(Angle.Unit.RAD);
        double rz = pose.getRotation().getRZ(Angle.Unit.RAD);
        ValueFactoryProvider valueFactoryProvider = apiProvider.getProgramAPI().getValueFactoryProvider();
        return valueFactoryProvider.getPoseFactory().createPose(x, y, z, rx, ry, rz, unit, Angle.Unit.RAD);
    }






    @Override
    public void openView() {

    }

    @Override
    public void closeView() {

    }

    @Override
    public String getTitle() {
        return "Quality Check";
    }

    @Override
    public boolean isDefined() {
        return false;
    }

    @Override
    public void generateScript(ScriptWriter writer) {

    }
}
