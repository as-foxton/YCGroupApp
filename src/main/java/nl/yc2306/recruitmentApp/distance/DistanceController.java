package nl.yc2306.recruitmentApp.distance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(maxAge = 3600)
public class DistanceController {
    @Autowired
    DistanceService distanceService;

    @RequestMapping("distance")
    public double calcDistance(@RequestParam String placeA, @RequestParam String placeB)throws LocatieNotFoundException{
        return distanceService.calculateDistance(placeA,placeB);
    }
}
