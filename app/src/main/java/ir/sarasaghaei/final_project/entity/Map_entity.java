package ir.sarasaghaei.final_project.entity;

//------------------------------------------------
//------------------------------------------------
//------------------------------------------------
//------------------------------------------------
//------------------------------------------------
//------------------------------------------------
//------------------------------------------------
//------------- for lode map to service----------




import com.google.android.gms.maps.GoogleMap;
import java.io.Serializable;

public class Map_entity implements Serializable {
    private GoogleMap googleMap;

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
