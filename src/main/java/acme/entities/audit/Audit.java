
package acme.entities.audit;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Audit extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}", message = "Letras de A-Z entre 1 y 3 veces, seguidas de tres numeros entre 0 y 9")
	private String					code;

	@NotBlank
	@Length(max = 101)
	private String					conclusion;

	@NotBlank
	@Length(max = 101)
	private String					strongPoints;

	@NotBlank
	@Length(max = 101)
	private String					weakPoints;

	@Size(min = 1)
	@OneToMany
	private List<AuditingRecord>	auditingRecords;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Mark mark() {
		final Map<Mark, Long> stats = this.auditingRecords.stream().map(recordAudit -> recordAudit.getMark()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		return stats.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).orElseThrow(null).getKey();

	}

}
