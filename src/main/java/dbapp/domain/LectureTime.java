package dbapp.domain;

import java.util.Date;

public class LectureTime implements Comparable<LectureTime> {

	private int period;
	private int lectureId;
	private Date startTime;
	private Date endTime;

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getLectureId() {
		return lectureId;
	}

	public void setLectureId(int lectureId) {
		this.lectureId = lectureId;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Override
	public int compareTo(LectureTime o) {

		if (this.getStartTime() != null && o.getStartTime() != null
				&& this.getStartTime().compareTo(o.getStartTime()) != 0)
			return this.getStartTime().compareTo(o.getStartTime());
		if (this.getStartTime() == null)
			return 1;
		if (o.getStartTime() == null)
			return -1;

		if (this.getEndTime() != null && o.getEndTime() != null
				&& this.getEndTime().compareTo(o.getEndTime()) != 0)
			return this.getEndTime().compareTo(o.getEndTime());
		if (this.getEndTime() == null)
			return 1;
		if (o.getEndTime() == null)
			return -1;

		if (((Integer) this.getPeriod()).compareTo(o.getPeriod()) != 0)
			return ((Integer) this.getPeriod()).compareTo(o.getPeriod());

		if (((Integer) this.getLectureId()).compareTo(o.getLectureId()) != 0)
			return ((Integer) this.getLectureId()).compareTo(o.getLectureId());

		return ((Integer) this.hashCode()).compareTo(o.hashCode());
	}
}
