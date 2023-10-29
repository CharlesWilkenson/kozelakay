package com.management.system.entities;

import java.util.Objects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_content")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Content extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String brief;

    @Column(nullable = false)
    private String content;

    @ManyToOne
	@JoinColumn(name = "author" , referencedColumnName = "email")
    private Member author;
    
    private String fileName;

	@Override
	public String toString() {
		return "Content [id=" + id + ", title=" + title + ", brief=" + brief + ", content=" + content  + ", fileName=" + fileName + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Content other = (Content) obj;
		return Objects.equals(author, other.author) && Objects.equals(brief, other.brief)
				&& Objects.equals(content, other.content) && Objects.equals(fileName, other.fileName)
				&& Objects.equals(id, other.id) && Objects.equals(title, other.title);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
		return result;
	}
    
    

}
