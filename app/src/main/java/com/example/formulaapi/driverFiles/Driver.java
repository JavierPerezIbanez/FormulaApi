    package com.example.formulaapi.driverFiles;

    import com.example.formulaapi.baseFiles.BaseEntity;
    import com.google.gson.annotations.SerializedName;

    public class Driver implements BaseEntity {
        @SerializedName("driverId")
        private String driverId;
        @SerializedName("name")
        private String name;
        @SerializedName("surname")
        private String surname;
        @SerializedName("nationality")
        private String nationality;
        @SerializedName("birthday")
        private String birthday;
        @SerializedName("number")
        private int number;
        @SerializedName("shortName")
        private String shortName;
        @SerializedName("url")
        private String url;

        private String fullName;

        @Override
        public String getId() {
            return driverId;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public String getNationality() {
            return nationality;
        }

        public String getBirthday() {
            return birthday;
        }

        public int getNumber() {
            return number;
        }

        public String getShortName() {
            return shortName;
        }

        public String getUrl() {
            return url;
        }

        public String getFullName() {
            return name+" "+surname;
        }
    }
