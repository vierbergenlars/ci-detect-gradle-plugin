package be.vbgn.gradle.cidetect;

class NullCiInformation implements CiInformation {


    @Override
    public boolean isCi() {
        return false;
    }
}
