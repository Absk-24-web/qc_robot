package com.synlabs.qc.impl.common;

public class Qc {

    public boolean setAlignmentControlPoint(Qc.ControlPoint ctrlPoint, Qc.Point3 point) throws Exception {
        String command = "sINT_300_";
        switch (ctrlPoint) {
            case A:
                command = command + "0";
                break;
            case B:
                command = command + "1";
                break;
            case C:
                command = command + "2";
                break;
            case D:
                command = command + "3";
                break;
            default:
                return false;
        }

        command = command + "_" + point.getX();
        command = command + "_" + point.getY();
        command = command + "_" + point.getZ();
        String response = "";
        return response.contains("rsINT 300 0".toLowerCase());

    }


    public static class Point3 {
        int[] point = new int[3];

        Point3(int x, int y, int z) {
            this.point[0] = x;
            this.point[1] = y;
            this.point[2] = z;
        }


        public int[] getPoint() {
            return this.point;
        }

        public int getX() {
            return this.point[0];
        }

        public int getY() {
            return this.point[1];
        }

        public int getZ() {
            return this.point[2];
        }
    }

    public  enum ControlPoint {
        A,
        B,
        C,
        D;

        ControlPoint() {
        }
    }

}
