package nl.yc2306.recruitmentApp.distance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kong.unirest.GenericType;
import kong.unirest.Unirest;

@Service
public class LocatieService {
    @Value("${location.url}")
    private String apiRoute;
    @Value("${location.api-key}")
    private String apiKey;
    @Autowired
    private ILocatieRepository locaties;

    public void addLocatie(Locatie nieuweLocatie){
        Locatie bewerkteLocatie = nieuweLocatie;
        bewerkteLocatie.setCity(bewerkteLocatie.getCity().toLowerCase());
        locaties.save(bewerkteLocatie);
    }

    public Locatie findLocatie(String city) throws LocatieNotFoundException{
        String cityName = city.toLowerCase();
        Locatie locatie = locaties.findByCity(cityName);
        if(locatie != null)
            return locatie;
        locatie = loadFromAPI(cityName);
        addLocatie(locatie);
        return locatie;
    }

    private Locatie loadFromAPI(String city) throws LocatieNotFoundException{
        List<ApiLocation> locaties = Unirest.get(apiRoute)
                .header("X-Api-Key", apiKey)
                .queryString("country", "NL")
                .queryString("city", city)
                .asObject(new GenericType<List<ApiLocation>>(){})
                .getBody();
        for (ApiLocation l: locaties) {
            if(l.getName().toLowerCase().equals(city)){
                Locatie locatie = new Locatie();
                locatie.setCity(city);
                locatie.setLatitude(l.getLatitude());
                locatie.setLongitude(l.getLongitude());
                return locatie;
            }
        }
        throw new LocatieNotFoundException(String.format("No city with the name %s was found",city));
    }
}
