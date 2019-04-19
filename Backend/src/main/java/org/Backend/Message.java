package org.Backend;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.stereotype.Component;


@Entity
@Table
@Component
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(columnDefinition = "SMALLDATETIME", updatable = false, nullable = false)
	private LocalDateTime sentAt;
	
	@Column(updatable = false, nullable = false)
	private String content;
	
	@Lob
	@Column(columnDefinition = "BINARY(1000)")
	private byte[] attachment;
	
	@Column
	private Long replyToId;
	
	@Column
	@OneToMany(mappedBy="msgId")
	private Set<MessageDetails> messageDetails;
	
	@Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	public Long getReplyToId() {
		return replyToId;
	}

	public void setReplyToId(Long replyToId) {
		this.replyToId = replyToId;
	}

	public Set<MessageDetails> getMessageDetails() {
		return messageDetails;
	}

	public void setMessageDetails(Set<MessageDetails> messageDetails) {
		this.messageDetails = messageDetails;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((sentAt == null) ? 0 : sentAt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (sentAt == null) {
			if (other.sentAt != null)
				return false;
		} else if (!sentAt.equals(other.sentAt))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", sentAt=" + sentAt + ", content=" + content + ", replyToId=" + replyToId + "]";
	}	
	

	
}
