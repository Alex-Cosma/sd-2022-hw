package com.policeSection.section;

import com.policeSection.section.Model.Section;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionService {
    private final SectionRepository repository;

    public Section create(Section s)
    {
        return repository.save(s);
    }
    public Section findByNumber(int nr)
    {
        return repository.findByNumber(nr);
    }

}
