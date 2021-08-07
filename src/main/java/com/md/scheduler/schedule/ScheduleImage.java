package com.md.scheduler.schedule;

import com.md.scheduler.commons.image.BaseImage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
class ScheduleImage extends BaseImage {

    public ScheduleImage(String path) {
        super(path);
    }
}
