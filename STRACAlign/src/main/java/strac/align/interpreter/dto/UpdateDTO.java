package strac.align.interpreter.dto;

import strac.align.models.AlignResultDto;

public class UpdateDTO {


    public Alignment mainDto;

    public AlignResultDto resultDto;

    public int overallProgres = 0;

    public static UpdateDTO instance;

    public UpdateDTO(Alignment mainDto, AlignResultDto resultDto, int overall){
        this.mainDto = mainDto;
        this.resultDto = resultDto;
        this.overallProgres = overall;

        instance = this;
    }
}
