CREATE TABLE apply_jobs (
    id UUID PRIMARY KEY,
    candidate_id UUID NOT NULL,
    job_id UUID NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (candidate_id) REFERENCES candidate(id),
    FOREIGN KEY (job_id) REFERENCES jobs(id)
);

CREATE INDEX idx_apply_jobs_candidate ON apply_jobs(candidate_id);
CREATE INDEX idx_apply_jobs_job ON apply_jobs(job_id);