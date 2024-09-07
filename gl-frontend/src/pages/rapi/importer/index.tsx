export default function ImporterPage() {
  return (
    <>
      <h1>Importer Page</h1>

      <p>Import anomaly interception data</p>
      <form action="">
        <input type="file" name="anomaly-data" id="" />
        <button type="submit">Import</button>
      </form>

      <br />

      <p>Import special interception data</p>
      <form action="">
        <input type="file" name="special-data" id="" />
        <button type="submit">Import</button>
      </form>

      <br />

      <p>Import manufacturer equipment data</p>
      <form action="">
        <input type="file" name="equipment-data" id="" />
        <button type="submit">Import</button>
      </form>
    </>
  )
}
