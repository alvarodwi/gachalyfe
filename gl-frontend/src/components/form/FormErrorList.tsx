import { FieldErrors } from 'react-hook-form'

interface Props {
  errors: FieldErrors
}

export default function FormErrorList(props: Props) {
  return (
    <>
      {Object.keys(props.errors).length > 0 && (
        <div className="flex w-full flex-col">
          <h3 className="text-sm">Form Errors:</h3>
          <ul className="text-xs text-gray-600">
            {Object.entries(props.errors).map(([field, error], index) => (
              <li className="ml-4 list-disc" key={index}>
                {field}:{' '}
                {typeof error?.message === 'string'
                  ? error.message
                  : 'unknown error'}
              </li>
            ))}
          </ul>
        </div>
      )}
    </>
  )
}
