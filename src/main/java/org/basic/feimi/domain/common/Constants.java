package org.basic.feimi.domain.common;

public class Constants {

    public static class Env {
        public static final String LOCAL = "LOCAL";
        public static final String DEV = "DEV";
        public static final String STG = "STG";
        public static final String PROD = "PROD";
    }

    public static class DeletedFlag {

        public static final Byte NOT_DELETED = 0;

        public static final Byte DELETED = 1;
    }

    public static class Role {

        public static final Byte ADMIN = 0;

        public static final Byte USER = 1;

    }

    public static class USER_STOP_STATUS {

        public static final Byte NORMAL = 0;

        public static final Byte STOP = 1;
    }

    public static class RegistrationStatus {

        public static final Byte REGISTER_HANDLE = 0;

        public static final Byte REGISTER_COMPLETE = 1;
    }
}
