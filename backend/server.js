const express = require('express')
const mongoose = require('mongoose')
const cors = require('cors')

const routes = require('./routes/ToDoRoute')

require('dotenv').config()

const app = express()
const PORT = process.env.port || 8000

app.use(express.json())
app.use(cors())

const MONGOURL = process.env.MONGO_URL;

mongoose.connect(MONGOURL).then(() => console.log(`Connected to MongoDB...`)).catch((err)=> console.log(err))
 app.use(routes)
app.listen(PORT, ()=> console.log(`Listening on: ${PORT}`))
