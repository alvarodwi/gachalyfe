import { Link } from "react-router-dom";

export default function ProgressionPage() {
  return (
    <>
      <h1>Account Progression Stats</h1>

      <Link to={"anomaly"}>
        <p>Add new anomaly interception</p>
      </Link>
      <Link to={"special"}>
        <p>Add new special interception</p>
      </Link>

      <h2>View various stats</h2>

      <p>Account creation : 2022/11/08</p>
      <p>Account age : x</p>
      <p>Total interception attempted : x</p>
      <p>Stats recorded from : 2023/06/17</p>

      <p>Total Drops</p>
      <table>
        <tr>
          <th>T9 Manufacturer Equipments</th>
          <td>x</td>
        </tr>
        <tr>
          <th>Custom Modules</th>
          <td>x</td>
        </tr>
      </table>
    </>
  );
}
