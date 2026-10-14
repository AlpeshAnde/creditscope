# CreditScope: Loan Risk and Credit Scoring Engine

A Spring Boot REST API that scores loan applications on a 300–850 scale, explains every score, and exposes SQL analytics on top of MySQL.

**Stack:** Java 21, Spring Boot 4.1.1, Spring Security (JWT), Spring Data JPA / Hibernate, MySQL 8, Maven, Lombok, JJWT.

## What it does
- Stores applicants, existing loans, repayment history and loan applications.
- Scores an application with a weighted, rule-based model and saves the **four factor scores** so each result is explainable.
- Assigns a risk band: **LOW** (>= 700), **MEDIUM** (>= 550), **HIGH** (below 550).
- Reports portfolio risk through SQL views, window functions, CTEs, a trigger and a stored procedure.
- Secures the API with stateless JWT authentication and BCrypt password hashing.

## Scoring model
`total = 300 + (weighted factor total / 100) * 550`

| Factor | Weight | How it is calculated |
|---|---|---|
| Payment history | 35% | % of repayments paid on time (50 if no history) |
| Debt-to-income | 30% | 100 minus outstanding debt as % of annual income, clamped 0–100 |
| Defaults | 20% | 20 if any existing loan is defaulted, else 100 |
| Employment | 15% | SALARIED 100, SELF_EMPLOYED 70, UNEMPLOYED 20 |

## Architecture
Layered monolith: `controller` -> `service` -> `repository` -> MySQL, with `dto`, `entity`, `enums`, `config` (security, JWT filter), `util` (JWT) and `exception` (global error handling).
The scoring logic sits in its own service (`CreditScoreService`) so it can be split into a separate service later.

## SQL analytics (`schema/`)
| File | Contents |
|---|---|
| `01_views.sql` | `v_latest_score`, `v_applicant_risk_summary` |
| `02_trigger_audit.sql` | Trigger that logs every loan application status change |
| `03_procedures.sql` | `sp_applicants_by_risk(band, limit)` |
| `04_reports.sql` | Window functions (`RANK`, `SUM() OVER`, moving average) and CTEs |
| `05_seed_data.sql` | 30 sample applicants with loans, repayments and applications |

## Run locally
1. Create the database: `CREATE DATABASE creditscope;`
2. Set environment variables: `DB_PASSWORD` and `JWT_SECRET` (a random string of at least 32 characters).
3. Start the app: `./mvnw spring-boot:run` (it runs on port 8082 and creates the tables).
4. Run the SQL files in `schema/` in order (the seed file is optional).
5. Log in, then score everything: `POST /api/creditScoreResult/calculate-all`.

## API (JWT required except login)
| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/auth/login` | Returns a JWT |
| CRUD | `/api/users`, `/api/applicant`, `/api/existingLoan`, `/api/loanApplication` | Manage data |
| POST | `/api/creditScoreResult/calculate/{loanApplicationId}` | Score one application |
| POST | `/api/creditScoreResult/calculate-all` | Score every application |
| GET | `/api/creditScoreResult`, `/api/scoreFactor` | View results and factor breakdowns |
| GET | `/api/reports/risk-distribution` | Count and % per risk band |
| GET | `/api/reports/top-risk-by-employment-type` | Three riskiest applicants per employment type |
| GET | `/api/reports/default-trend` | Monthly missed payments with a 3-month moving average |

Send the token as `Authorization: Bearer <token>`.
Errors return JSON with the right status: 401 bad credentials, 409 duplicate data, 400 invalid request.

## Roadmap
- Replace the catch-all 400 with a `ResourceNotFoundException` returning 404.
- Unit tests for the scoring service.
- Docker Compose for the API and MySQL.
- Extract scoring into its own service.