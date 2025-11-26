# My Shop (React + TypeScript)

Simple frontend for the interview task. Built with Vite + React + TypeScript.

## Requirements
- Node.js 18+ and npm

## Setup
1. Install dependencies:
```bash
npm install
```
2. Start dev server:
```bash
npm run dev
```
3. Open http://localhost:5173

## Configuration
The frontend expects a backend API base URL in environment variable `VITE_API_BASE`.
Create a `.env` file with:
```
VITE_API_BASE=http://localhost:8080/api
```

## Notes
- Uses a fake `USER_ID = 1` for demo.
- Endpoints used should match the backend described in the task.
