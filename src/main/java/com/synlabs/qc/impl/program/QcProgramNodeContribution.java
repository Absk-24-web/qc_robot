package com.synlabs.qc.impl.program;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.domain.userinteraction.robot.movement.MovementCompleteEvent;
import com.ur.urcap.api.domain.userinteraction.robot.movement.RobotMovement;
import com.ur.urcap.api.domain.userinteraction.robot.movement.RobotMovementCallback;
import com.ur.urcap.api.domain.value.Pose;
import com.ur.urcap.api.domain.value.ValueFactoryProvider;
import com.ur.urcap.api.domain.value.jointposition.JointPositions;
import com.ur.urcap.api.domain.value.simple.Angle;
import com.ur.urcap.api.domain.value.simple.Length;

import javax.tools.Tool;

public class QcProgramNodeContribution implements ProgramNodeContribution {

    private  RobotMovement robotMovement;
    private DataModel model;
    private ProgramAPIProvider apiProvider;
    private QcProgramNodeView view;
    private URCapAPI urCapAPI;
    private static final String TOOL_CHANGE_JOINT_POSITIONS = "joint-position";



    public QcProgramNodeContribution(ProgramAPIProvider apiProvider, QcProgramNodeView view, DataModel model) {
        this.model = model;
        this.apiProvider = apiProvider;
        robotMovement = apiProvider.getUserInterfaceAPI().getUserInteraction().getRobotMovement();
    }

    public QcProgramNodeContribution() {

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


    public void moveTOPosition(){
        robotMovement.requestUserToMoveRobot(getToolChangeJointPositions(), new RobotMovementCallback() {
            @Override
            public void onComplete(MovementCompleteEvent event) {

            }
        });
    }

    public JointPositions getToolChangeJointPositions() {
        return model.get(TOOL_CHANGE_JOINT_POSITIONS, (JointPositions) null);
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
