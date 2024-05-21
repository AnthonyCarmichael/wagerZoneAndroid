<?php
require 'vendor/autoload.php';
if(1){//isset($_POST['authKey'] && ($_POST['authKey'] != null)){
    $stripe = new \Stripe\StripeClient('sk_test_51PIkR1FpLIWsuWPlwXdXlAJ8GekiLjt5e9KReyoEJ3IHY1GGVgTPpltKOCOjjwN44zHGpFFWr4ULpodnwO5v2Oh200vO91Lxxo');

    // Use an existing Customer ID if this is a returning customer.
    $customer = $stripe->customers->create(
        [
            'name' => 'Bob',
            'adresse' => [
                'line1' => 'Demo address',
                'postal_cole' => 'T3S 1A3',
                'city' => 'Monthreal',
                'Province' => 'QC',
                'country' => 'CA',
            ]
        ]
    );
    $ephemeralKey = $stripe->ephemeralKeys->create([
      'customer' => $customer->id,
    ], [
      'stripe_version' => '2024-04-10',
    ]);
    $paymentIntent = $stripe->paymentIntents->create([
      'amount' => 1099, //10.99 cad//$_POST['montant']*100,
      'currency' => 'cad',
      'description' => 'Paiement WagerZone',
      'customer' => $customer->id,
      // In the latest version of the API, specifying the `automatic_payment_methods` parameter
      // is optional because Stripe enables its functionality by default.
      'automatic_payment_methods' => [
        'enabled' => 'true',
      ],
    ]);

    echo json_encode(
      [
        'paymentIntent' => $paymentIntent->client_secret,
        'ephemeralKey' => $ephemeralKey->secret,
        'customer' => $customer->id,
        'publishableKey' => 'pk_test_51PIkR1FpLIWsuWPl3GNvxBuZDvdzEDJhAXvpLdho7ZYLNsBqjEoF5HVMNcvii4CFNavFERGsGr6STaJfwmQ7JpbB005NeRQS4u'
      ]
    );
    http_response_code(200);
}echo "non autorisé"