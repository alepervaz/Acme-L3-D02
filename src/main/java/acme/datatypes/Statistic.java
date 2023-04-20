package acme.datatypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Statistic {

    // Attributes -------------------------------------------------------------
    int		count;

    double	average;

    double	max;

    double	min;

    double deviation;
}
