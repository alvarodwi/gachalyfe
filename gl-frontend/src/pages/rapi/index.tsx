import { Link } from "react-router-dom";

export default function RapiPage() {
  return (
    <>
      <h1>Rapi</h1>
      <p>Tracking NIKKE account data</p>

      <Link to={"progression"}>
        <p>View progression</p>
      </Link>
      <Link to={"inventory"}>
        <p>View inventory</p>
      </Link>
      <Link to={"gacha"}>
        <p>View gacha stats</p>
      </Link>
    </>
  );
}
