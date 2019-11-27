package com.synlabs.qc.impl.installation;

import com.synlabs.qc.impl.common.RobotRealtimeReader;
import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.installation.CreationContext;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.domain.ProgramAPI;
import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.domain.undoredo.UndoRedoManager;
import com.ur.urcap.api.domain.userinteraction.robot.movement.MovementCompleteEvent;
import com.ur.urcap.api.domain.userinteraction.robot.movement.RobotMovement;
import com.ur.urcap.api.domain.userinteraction.robot.movement.RobotMovementCallback;
import com.ur.urcap.api.domain.value.jointposition.JointPositions;

import java.text.DecimalFormat;


public class QcInstallationNodeContribution implements InstallationNodeContribution {

    private final DataModel model;
    public String showTcp,s;
    private QcInstallationNodeView view;
    public  StringBuilder strPose = new StringBuilder() ;
    public int count = 0;
    private URCapAPI urCapAPI;
    private ProgramAPI api;
    private UndoRedoManager undoRedoManager;
    private RobotMovement robotMovement;
    private static final String TOOL_CHANGE_JOINT_POSITIONS = "joint-position";




    public QcInstallationNodeContribution(InstallationAPIProvider apiProvider, QcInstallationNodeView view, DataModel model, CreationContext context ) {
        this.model = model;
        this.urCapAPI = urCapAPI;
        this.view = view;
        this.api = api;
        robotMovement = apiProvider.getUserInterfaceAPI().getUserInteraction().getRobotMovement();

    }

    // Creating handle to getRobotRealtimeData class
    private RobotRealtimeReader realtimeReader = new RobotRealtimeReader();
    public  void getPose(){

        // Used to read a snapshot from the Real Time Client
        // Should be called at least once before accessing data.
        realtimeReader.readNow();

        // Create a decimal formatter, to decide how many digits after decimal separator
        DecimalFormat df = new DecimalFormat("#.####");

        // Read the actual TCP pose
        double[] tcp = realtimeReader.getActualTcpPose();
        showTcp = "["+
                df.format(tcp[0])+","+
                df.format(tcp[1])+"]";
//                df.format(tcp[2])+","+
//                df.format(tcp[3])+","+
//                df.format(tcp[4])+","+
//                df.format(tcp[5])+"]";
                count++;
            strPose.append(showTcp+",");
            s = strPose.toString();
            System.out.println(s);
        if(count == 4 ){
            count = 0;
            strPose.delete(0,strPose.length());

        }

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

















//    void addWaypointNode() {
//        ProgramAPI programAPI = (ProgramAPI) api.getProgramModel();
//        ProgramModel programModel = programAPI.getProgramModel();
//        ProgramNodeFactory nf = programModel.getProgramNodeFactory();
//        ValueFactoryProvider valueFactoryProvider = api.getValueFactoryProvider();
//
//        realtimeReader.readNow();
//        double[] tcp_pose = realtimeReader.getActualTcpPose(); // Read the actual TCP pose
//        double[] tcp_jointPositions = realtimeReader.getActualJointPose(); // Read the actual Joint pose
//        final Pose[] pose = new Pose[1];
//        pose[0] = valueFactoryProvider.getPoseFactory().createPose(tcp_pose[0], tcp_pose[1], tcp_pose[2], tcp_pose[3], tcp_pose[4], tcp_pose[5], Length.Unit.M, Angle.Unit.RAD);
//        final JointPositions[] jointPositions = new JointPositions[1];
//        jointPositions[0] = valueFactoryProvider.getJointPositionFactory().createJointPositions(tcp_jointPositions[0], tcp_jointPositions[1], tcp_jointPositions[2], tcp_jointPositions[3], tcp_jointPositions[4], tcp_jointPositions[5], Angle.Unit.RAD);
//
//        JointPosition[] jointPosition = jointPositions[0].getAllJointPositions();
//
//        double[] jointPositionsArray1 = new double[6];
//        double[] jointPositionsArray2 = new double[6];
//        for (int i = 0; i < 6; i++) {
//            jointPositionsArray1[i] = jointPosition[i].getAngle(Angle.Unit.RAD);
//            jointPositionsArray2[i] = jointPositions[0].getAllJointPositions()[i].getAngle(Angle.Unit.RAD);
//        }
//
//        System.out.println("p" + dobleArrayToString(tcp_pose) + " [m, rad]");
//        System.out.println("j" + dobleArrayToString(tcp_jointPositions) + " [rad]");
//        System.out.println("p" + dobleArrayToString(pose[0].toArray(Length.Unit.M, Angle.Unit.RAD)) + " [m, rad] PoseFactory");
//        System.out.println("j" + dobleArrayToString(jointPositionsArray1) + " [rad] JointPositionFactory");
//        System.out.println("j" + dobleArrayToString(jointPositionsArray2) + " [rad] JointPositionFactory");
//
//        final TreeNode[] root = new TreeNode[1];
//        root[0] = programModel.getRootTreeNode((ProgramNodeContribution) this);
//
//        final MoveNode[] moveNode = new MoveNode[1];
//        moveNode[0] = null;
//
//        final TreeNode[] moveTreeNode = new TreeNode[1];
//        moveTreeNode[0] = null;
//
//        final WaypointNode[] waypointNode = new WaypointNode[1];
//        waypointNode[0] = null;
//
//        if (root[0].getChildren().isEmpty()) {
//
//            moveNode[0] = nf.createMoveNode(); //It creates a Waypoint inside it too
//
//            undoRedoManager.recordChanges( new UndoableChanges() {
//                @Override
//                public void executeChanges() {
//
//                    try {
//                        try {
//                            moveTreeNode[0] = root[0].addChild(moveNode[0]);
//                        } catch (TreeStructureException e) {
//                            e.printStackTrace();
//                        }
//                        root[0].setChildSequenceLocked(true);
//                        waypointNode[0] = (WaypointNode) moveTreeNode[0].getChildren().get(0).getProgramNode();
//                        waypointNodeConfigFactory = waypointNode[0].getConfigFactory();
//                        BlendParameters blendParameters = waypointNodeConfigFactory.createNoBlendParameters();
//                        WaypointMotionParameters motionParameters = waypointNodeConfigFactory.createSharedMotionParameters();
//                        WaypointNodeConfig newWaypointNodeConfig = waypointNodeConfigFactory.createFixedPositionConfig(pose[0], jointPositions[0], blendParameters, motionParameters);
//                        waypointNode[0].setConfig(newWaypointNodeConfig);
//                    } catch (TreeStructureException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        } else {
//            moveTreeNode[0] = root[0].getChildren().get(0);
//
//            //Add Waypoint inside
//            waypointNode[0] = nf.createWaypointNode();
//
//            undoRedoManager.recordChanges( new UndoableChanges() {
//                @Override
//                public void executeChanges() {
//
//                    try {
//                        moveTreeNode[0].addChild(waypointNode[0]);
//                        waypointNodeConfigFactory = waypointNode[0].getConfigFactory();
//                        BlendParameters blendParameters = waypointNodeConfigFactory.createNoBlendParameters();
//                        WaypointMotionParameters motionParameters = waypointNodeConfigFactory.createSharedMotionParameters();
//                        WaypointNodeConfig newWaypointNodeConfig = waypointNodeConfigFactory.createFixedPositionConfig(pose[0], jointPositions[0], blendParameters, motionParameters);
//                        waypointNode[0].setConfig(newWaypointNodeConfig);
//                    } catch (TreeStructureException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//    }








//    private void selectCenterPoint() {
//        {
//
//            Length.Unit unit1 = Length.Unit.M;
//            double[] tcp = new double[266];
//            tcp = getData.getActualTcpPose();
//            double[] jts = new double[266];
//            jts = getData.getActualJointPose();
//
//            //removeNodes();
//            //createNodes();
//            // configureMoveNode();
//
//            Pose pose = urCapAPI.getValueFactoryProvider().getPoseFactory().createPose(tcp[0], tcp[1], tcp[2],
//                    tcp[3], tcp[4], tcp[5], unit1, Angle.Unit.RAD);
//
//            JointPositions jointPositions = urCapAPI.getValueFactoryProvider().getJointPositionFactory().createJointPositions(jts[0], jts[1], jts[2], jts[3],
//                    jts[4], jts[5], Angle.Unit.RAD);
//
//            // adjustWaypointsToCenterPoint(pose, jointPositions);
//        }
//    }








        @Override
    public void openView() {

    }

    @Override
    public void closeView() {

    }

    @Override
    public void generateScript(ScriptWriter writer) {

    }


}
