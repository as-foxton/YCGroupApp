package nl.yc2306.recruitmentApp.distance;

import nl.yc2306.recruitmentApp.CurriculumVitae;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DistanceService {
    @Autowired
    LocatieService locatieService;
    public double calculateDistance(String placeA, String placeB)throws LocatieNotFoundException{
        Locatie a = locatieService.findLocatie(placeA);
        Locatie b = locatieService.findLocatie(placeB);

        double lon1 = Math.toRadians(a.getLongitude());
        double lon2 = Math.toRadians(b.getLongitude());
        double lat1 = Math.toRadians(a.getLatitude());
        double lat2 = Math.toRadians(b.getLatitude());

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double d = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(d));

        double r = 6371;

        return(c * r);
    }

    public Iterable<? extends HasLocatie> filterDistance(Iterable<? extends HasLocatie> cvs, String plaats, int maxAfstand){
        ArrayList<HasLocatie> response = new ArrayList<>();
        for(HasLocatie place :cvs){
            try {
                double afstand = calculateDistance(plaats, place.getLocatie());
                if(afstand <= maxAfstand)
                    response.add(place);
            }
            catch (LocatieNotFoundException e){
                System.out.printf("een van de plaatsen is niet gevonden: %s, %s",plaats,place.getLocatie());
            }
        }
        return response;
    }
}
