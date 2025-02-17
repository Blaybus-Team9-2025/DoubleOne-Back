package org.doubleone.domain.endMatching.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.doubleone.global.BaseTimeEntity;

@Entity
@Table(name = "end_matching")
@Getter
@Log4j2
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EndMatching extends BaseTimeEntity {

}
