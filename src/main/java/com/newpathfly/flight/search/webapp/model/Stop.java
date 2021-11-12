package com.newpathfly.flight.search.webapp.model;

import com.newpathfly.model.Segment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stop {
    
    Segment inboundSegment;
    Segment outboundSegment;
}
