package dev.sudipsaha;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PresentationTools {
    private List<Presentation> presentations = new ArrayList<>();
    public PresentationTools() {
        var keynoteOne = new Presentation("Presentation one", "https://www.youtube.com/watch?v=VDhQFBxIgtI", 2025);
        var keynoteTwo = new Presentation("Presentation two", "https://www.youtube.com/watch?v=lHgtB7DuOD8", 2023);

        this.presentations.addAll(List.of(keynoteOne, keynoteTwo));

    }
    public List<Presentation> getPresentations(){
        return presentations;
    }

    public List<Presentation> getPresentationByYear(int year) {
        return presentations.stream().filter(p -> p.year() == year).toList();
    }

    public List<Map<String, Object>> getPresentationAsMapList(){
        return presentations.stream()
                .map(p -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("title", p.title());
                    map.put("url", p.url());
                    map.put("year",p.year());
                    return map;
                }).collect(Collectors.toUnmodifiableList());
    }
}
