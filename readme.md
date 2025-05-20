# AccServer

**AccServer** is an experimental microservice designed to test custom architectural concepts for packet-safe data handling in high-concurrency environments. It was originally built as part of a technical exercise.

Unlike most Java microservices, AccServer **does not rely on Spring** or external frameworks. Instead, it showcases **pure Java architecture** focused on **controlled concurrency**, **double buffering**, and **safe packet streaming** using custom-written modules.

## ✨ Key Features

- **Framework-free design** — No Spring, no annotations, full manual control over lifecycle
- **Envelope-based double buffering** — Inspired by safe concurrent data replication
- **Parallel processing flow** — Threads work over packets and flow stages
- **Simple, readable structure** — For educational, experimental and architectural exploration
- **Maven project** — Can be built and run standalone

## 📦 Technologies

- Java 8
- Maven
- JUnit (basic testing)
- Pure Java threading and control

## 🚀 Getting Started

```bash
git clone https://github.com/swampus/accserver
cd accserver
mvn compile
```

To run the main process:

```bash
mvn exec:java -Dexec.mainClass="io.github.swampus.accserver.core.ServerRunner"
```

> Note: adjust `exec.mainClass` if you add your own runner or test tool.


## 📚 Motivation

This project was designed as a custom implementation of a packet-processing server, where the architecture itself becomes the primary value — not frameworks or external integrations. It serves both as a testbed for concurrency logic and a study case in code architecture purity.

## ❗ Disclaimer

This is not a production-grade service, but a **clean-room implementation** meant for architectural experimentation and educational purposes.

---

Created with care by [@swampus](https://github.com/swampus)
