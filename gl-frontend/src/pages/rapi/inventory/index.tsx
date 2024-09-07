import { Link } from "react-router-dom";

export default function InventoryPage() {
  return (
    <>
      <h1>Account Inventory Stats</h1>

      <Link to={"arms"}>
        <p>Open new manufacturer arms</p>
      </Link>
      <Link to={"furnace"}>
        <p>Open new manufacturer furnace</p>
      </Link>

      <h2>View various stats</h2>

      <p>Total manufacturer arms opened : x</p>
      <p>Total manufacturer furnace opened : x</p>
      <p>Stats recorded from : 2023/06/17</p>

      <p>Total Drops</p>
      <table>
        <tr>
          <td>Elysion Attacker Helmet</td>
          <td>Elysion Attacker Torso</td>
          <td>Elysion Attacker Gloves</td>
          <td>Elysion Attacker Boots</td>
        </tr>
        <tr>
          <td>x</td>
        </tr>
      </table>
    </>
  );
}
