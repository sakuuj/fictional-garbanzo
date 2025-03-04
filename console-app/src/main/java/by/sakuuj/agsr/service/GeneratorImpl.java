package by.sakuuj.agsr.service;

import by.sakuuj.agsr.domain.PatientRequest;
import org.instancio.Instancio;
import org.instancio.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class GeneratorImpl implements Generator {

    public PatientRequest generatePatient() {

        return Instancio.of(PatientRequest.class)
                .generate(
                        Select.field(PatientRequest.class, "birthDate"),
                        gen -> gen.temporal().instant().past()
                )
                .generate(
                        Select.field(PatientRequest.class, "gender"),
                        gen -> gen.oneOf("male", "female")
                )
                .generate(
                        Select.field(PatientRequest.class, "name"),
                        gen -> gen.text().wordTemplate("${Noun} ${Noun} ${Noun}")
                )
                .create();
    }

    public List<PatientRequest> generatePatients(int count) {

       List<PatientRequest> patients = new ArrayList<>(count);

        IntStream.range(0, count)
                .forEach(i -> patients.add(generatePatient()));

        return patients;
    }
}
